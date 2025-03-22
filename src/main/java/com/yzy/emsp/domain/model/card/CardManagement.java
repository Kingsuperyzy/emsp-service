package com.yzy.emsp.domain.model.card;


import com.yzy.emsp.domain.model.account.Account;
import com.yzy.emsp.domain.model.account.AccountNumber;
import com.yzy.emsp.domain.model.account.AccountRepository;
import org.springframework.stereotype.Component;


@Component
public class CardManagement {
    private AccountRepository accountRepository;

    public CardManagement(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account findByAccountNo(String userNo) {
        Account account = accountRepository.findByAccountNo(AccountNumber.of(userNo));
        return account;
    }
}
