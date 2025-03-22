package com.yzy.emsp.domain.model.card;



import com.yzy.emsp.domain.model.account.AccountStatus;

import java.util.Date;
import java.util.List;

public interface CardRepository {
    void save(Card product);

    void update(Card product);

    List<Card> findByCreateTimeBetween(Date startTime, Date endTime, int page, int size);

    long countByCreateTimeBetween(Date startTime, Date endTime);

    Card findByCardNo(CardNumber cN);

    Integer syncCardStatusWithAccount(String accountNo, CardStatus status);

}
