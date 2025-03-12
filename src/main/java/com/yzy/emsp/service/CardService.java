package com.yzy.emsp.service;

import com.yzy.emsp.domain.CardStatus;
import com.yzy.emsp.domain.dto.CardDTO;
import com.yzy.emsp.domain.vo.CardVO;
import com.yzy.emsp.utils.PageResult;

import java.util.Date;

public interface CardService {

    CardVO createCard(CardDTO dto);

    Boolean assignCard(Integer cardId, Integer userId);

    CardVO updateCardStatus(Integer cardId, CardStatus status);

    PageResult<CardVO> searchByUpdateTime(Date startTime,Date endTime, int page, int size);

}
