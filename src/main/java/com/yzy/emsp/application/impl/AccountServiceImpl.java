package com.yzy.emsp.application.impl;


import com.yzy.emsp.application.AccountService;
import com.yzy.emsp.application.command.CreateAccountCommand;


import com.yzy.emsp.domain.model.account.*;
import com.yzy.emsp.domain.model.card.CardStatus;
import com.yzy.emsp.exception.BusinessException;


import com.yzy.emsp.ui.results.PageResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    private AccountManagement accountManagement;


    public AccountServiceImpl(AccountRepository accountRepository, AccountManagement accountManagement) {
        this.accountRepository = accountRepository;
        this.accountManagement = accountManagement;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public PageResult<Account> searchAccountsByUpdateTime(Date startTime, Date endTime, int page, int size) {

        List<Account> accountVOs = accountRepository.findByCreateTimeBetween(startTime, endTime, page, size);
        long total = accountRepository.countByCreateTimeBetween(startTime, endTime);
        return new PageResult<>(
                accountVOs,
                total,
                page,
                size
        );
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Account createAccount(CreateAccountCommand command) {
        // Check email uniqueness
        Long count = accountRepository.findByEmail(command.getEmail());
        if (count > 0) {
            throw new BusinessException("Email already registered");
        }
        // Check if the Contract ID already exists
        if (!StringUtils.isEmpty(command.getContractId())) {
            EMAID emaid = EMAID.of(command.getContractId());
            Long contractIdCount = accountRepository.findByContractId(emaid);
            if (contractIdCount > 0) {
                throw new BusinessException("Contract ID already exists");
            }
        }
        Account account = Account.of(command);
        accountRepository.save(account);
        return account;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Account updateStatus(String accountNo, AccountStatus newStatus) {
        // 1. Check if the account exists
        AccountNumber aN = AccountNumber.of(accountNo);
        Account account = accountRepository.findByAccountNo(aN);
        if (account == null) {
            throw new BusinessException("The account does not exist");
        }

        // 2. Check if the account is already in the target status
        if (account.getStatus().getCode().equals(newStatus.getCode())) {
            throw new BusinessException("Account is already in the target status");
        }

        // 3. Validate the status transition
        validateStatusTransition(account.getStatus(), newStatus);

        // 4. Update the account status
        if (newStatus == AccountStatus.INACTIVE) {
            account.deactivated();

            accountManagement.syncCardStatusWithAccount(accountNo,CardStatus.fromCode(3));
        } else {
            account.activated();
            accountManagement.syncCardStatusWithAccount(accountNo,CardStatus.fromCode(2));
        }
        accountRepository.update(account);
        return account;
    }


    /**
     * Validate if the status transition is allowed.
     *
     * @param currentStatus The current status of the account.
     * @param newStatus     The target status to transition to.
     * @throws BusinessException If the transition is not allowed.
     */
    private void validateStatusTransition(AccountStatus currentStatus, AccountStatus newStatus) {
        switch (currentStatus) {
            case INACTIVE:
                if (newStatus != AccountStatus.ACTIVATED) {
                    throw new BusinessException("Only ACTIVATED status is allowed from INACTIVE");
                }
                break;
            case ACTIVATED:
                if (newStatus != AccountStatus.INACTIVE) {
                    throw new BusinessException("Only INACTIVE status is allowed from ACTIVATED");
                }
                break;
            default:
                throw new BusinessException("Invalid status transition");
        }
    }


}
