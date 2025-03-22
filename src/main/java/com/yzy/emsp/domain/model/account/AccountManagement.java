package com.yzy.emsp.domain.model.account;


import com.yzy.emsp.domain.model.card.CardRepository;
import com.yzy.emsp.domain.model.card.CardStatus;
import org.springframework.stereotype.Component;


@Component
public class AccountManagement {
    private CardRepository cardRepository;

    public AccountManagement(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }


    public void syncCardStatusWithAccount(String accountNo, CardStatus status) {
        cardRepository.syncCardStatusWithAccount(accountNo,status);
    }

}
