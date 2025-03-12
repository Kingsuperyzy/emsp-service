package com.yzy.emsp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzy.emsp.domain.AccountStatus;
import com.yzy.emsp.domain.dto.AccountDTO;
import com.yzy.emsp.domain.vo.AccountVO;
import com.yzy.emsp.exception.BusinessException;
import com.yzy.emsp.mapper.AccountMapper;
import com.yzy.emsp.domain.entity.Account;
import com.yzy.emsp.service.AccountService;
import com.yzy.emsp.utils.EMAIDUtil;
import com.yzy.emsp.utils.EmailValidator;
import com.yzy.emsp.utils.PageResult;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {



    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @Override
    public PageResult<AccountVO> searchAccountsByUpdateTime(Date startTime,Date endTime, int page, int size) {
        // 1. Create a pagination object
        Page<Account> pageParam = new Page<>(page, size);

        // 2. Query accounts updated after the specified time
        LambdaQueryWrapper<Account> wrapper = new LambdaQueryWrapper<>();
        if (startTime != null && endTime != null) {
            wrapper.between(Account::getUpdateTime, startTime, endTime);
        } else if (startTime != null) {
            wrapper.ge(Account::getUpdateTime, startTime);
        } else if (endTime != null) {
            wrapper.le(Account::getUpdateTime, endTime);
        }
        IPage<Account> accountPage = baseMapper.selectPage(pageParam, wrapper);

        // 3. Convert entities to VOs
        List<AccountVO> accountVOs = accountPage.getRecords().stream()
                .map(AccountVO::fromEntity)
                .collect(Collectors.toList());

        // 4. Build the pagination result
        return new PageResult<>(
                accountVOs,
                accountPage.getTotal(),
                page,
                size
        );
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public AccountVO createAccount(AccountDTO dto) {
        // Check email uniqueness
        Integer count = baseMapper.selectCount(
                new QueryWrapper<Account>().eq("email", dto.getEmail())
        );
        if (count > 0) {
            throw new BusinessException("Email already registered");
        }
        if (!EmailValidator.isValidEmail(dto.getEmail())) {
            throw new BusinessException("Invalid email format");
        }
        String contractId = dto.getContractId();
        if (contractId == null || contractId.isEmpty()) {
            // Generate a new Contract ID if not provided
            dto.setContractId(EMAIDUtil.generateEMAID(true));
        } else {
            // Validate the provided Contract ID
            if (!EMAIDUtil.isValidEMAID(contractId)) {
                throw new BusinessException("Invalid Contract ID format");
            }
            // Normalize the Contract ID
            contractId = EMAIDUtil.normalizeEMAID(contractId);

            // Check if the Contract ID already exists
            Integer contractIdCount = baseMapper.selectCount(
                    new QueryWrapper<Account>().eq("contract_id", contractId)
            );
            if (contractIdCount > 0) {
                throw new BusinessException("Contract ID already exists");
            }
        }
        // Encrypt password
        Account account = new Account();
        BeanUtils.copyProperties(dto, account);
        account.setPassword(passwordEncoder.encode(dto.getPassword()));
        account.setStatus(AccountStatus.INACTIVE);
        account.setCreateTime(new Date());
        baseMapper.insert(account);
        return convertToVO(account);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AccountVO updateStatus(Integer accountId, AccountStatus newStatus) {
        // 1. Check if the account exists
        Account account = baseMapper.selectById(accountId);
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
        account.setStatus(newStatus);
        account.setUpdateTime(new Date());
        baseMapper.updateById(account);

        // 5. Return the updated account information
        return AccountVO.fromEntity(account);
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

    private AccountVO convertToVO(Account account) {
        AccountVO vo = new AccountVO();
        BeanUtils.copyProperties(account, vo);
        vo.setStatus(account.getStatus().getDescription());
        return vo;
    }
}
