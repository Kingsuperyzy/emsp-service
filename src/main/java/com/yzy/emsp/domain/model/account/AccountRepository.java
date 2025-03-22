package com.yzy.emsp.domain.model.account;

import java.util.Date;
import java.util.List;

public interface AccountRepository {
    void save(Account product);

    void update(Account product);

    List<Account> findByCreateTimeBetween(Date startTime, Date endTime, int page, int size);

    long countByCreateTimeBetween(Date startTime, Date endTime);

    long  findByEmail(String email);

    Account findByAccountNo(AccountNumber accountNo);

    Long findByContractId(EMAID contractId);
}
