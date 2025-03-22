package com.yzy.emsp.application;

import com.yzy.emsp.application.command.CreateAccountCommand;
import com.yzy.emsp.domain.model.account.Account;
import com.yzy.emsp.domain.model.account.AccountStatus;
import com.yzy.emsp.ui.results.PageResult;


import java.util.Date;

public interface AccountService {
    Account createAccount(CreateAccountCommand command);

    Account updateStatus(String accountNo, AccountStatus status);

    PageResult<Account> searchAccountsByUpdateTime(Date startTime, Date endTime, int page, int size);
}
