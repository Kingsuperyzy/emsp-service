package com.yzy.emsp.application;

import com.yzy.emsp.application.command.CreateCardCommand;

import com.yzy.emsp.domain.model.card.Card;
import com.yzy.emsp.domain.model.card.CardStatus;
import com.yzy.emsp.ui.results.PageResult;

import java.util.Date;

public interface CardService {

    Card createCard(CreateCardCommand createCardCommand);

    Card assignCard(String cardNo, String userNo);

    Card updateCardStatus(String cardNo, CardStatus status);

    PageResult<Card> searchByUpdateTime(Date startTime, Date endTime, int page, int size);

}
