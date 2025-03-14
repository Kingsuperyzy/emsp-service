package com.yzy.emsp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzy.emsp.domain.CardStatus;
import com.yzy.emsp.domain.dto.CardDTO;
import com.yzy.emsp.domain.entity.Account;
import com.yzy.emsp.domain.entity.Card;
import com.yzy.emsp.domain.query.CardQuery;
import com.yzy.emsp.domain.vo.CardVO;
import com.yzy.emsp.exception.BusinessException;
import com.yzy.emsp.mapper.AccountMapper;
import com.yzy.emsp.mapper.CardMapper;
import com.yzy.emsp.service.CardService;
import com.yzy.emsp.utils.PageResult;
import com.yzy.emsp.utils.RFIDCardUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

// CardServiceImpl.java
@Service
public class CardServiceImpl extends ServiceImpl<CardMapper, Card>
        implements CardService {

    @Autowired
    private AccountMapper accountMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public CardVO createCard(CardDTO dto) {
        // 1. Validate the input DTO
        if (dto == null) {
            throw new BusinessException(" card data cannot be null");
        }


        // 2. Map DTO to Entity
        Card card = new Card();
        BeanUtils.copyProperties(dto, card);


        // 3. Set default values
        if (dto.getBalance() == null) {
            card.setBalance(BigDecimal.ZERO); // Default balance is 0
        }
        if (dto.getIssueDate() == null) {
            card.setIssueDate(new Date()); // Default issue date is current time
        }
        card.setUid(RFIDCardUtil.generateUID());
        card.setCardNumber(RFIDCardUtil.generateVisibleId());

        // 4. Set initial status
        card.setStatus(CardStatus.CREATED);


        // 5. Save the card to the database
        baseMapper.insert(card);

        // 6. Return the created card as a VO
        return CardVO.fromEntity(card);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean assignCard(Integer cardId, Integer userId) {
        Card card = getById(cardId);

        if (card == null) {
            throw new BusinessException("Card not found");
        }
        Account account = accountMapper.selectById(userId);
        if (account == null) {
            throw new BusinessException("user not found");
        }

        if (card.getStatus() != CardStatus.CREATED) {
            throw new BusinessException("Card cannot be assigned in current status");
        }

        card.setUserId(userId);
        card.setStatus(CardStatus.ASSIGNED);
        return !updateById(card);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CardVO updateCardStatus(Integer cardId, CardStatus newStatus) {
        // 1. Check if the card exists
        Card card = baseMapper.selectById(cardId);
        if (card == null) {
            throw new BusinessException("Card not found");
        }

        // 2. Check if the card is already in the target status
        if (card.getStatus() == newStatus) {
            throw new BusinessException("Card is already in the target status");
        }

        // 3. Validate the status transition
        validateStatusTransition(card.getStatus(), newStatus);

        // 4. Update the card status
        card.setStatus(newStatus);
        card.setUpdateTime(new Date());
        baseMapper.updateById(card);

        // 5. Return the updated card information
        return CardVO.fromEntity(card);
    }

    @Override
    public PageResult<CardVO> searchByUpdateTime(Date startTime, Date endTime, int page, int size) {
        // 1. Create a pagination object
        Page<Card> pageParam = new Page<>(page, size);

        // 2. Query cards updated after the specified time
        CardQuery cardQuery = new CardQuery();
        cardQuery.setStartTime(startTime);
        cardQuery.setEndTime(endTime);
        IPage<Card> cardPage = baseMapper.selectCardPage(pageParam, cardQuery);

        // 3. Convert entities to VOs
        List<CardVO> cardVOs = cardPage.getRecords().stream()
                .map(CardVO::fromEntity)
                .collect(Collectors.toList());

        // 4. Build the pagination result
        return new PageResult<>(
                cardVOs,
                cardPage.getTotal(),
                page,
                size
        );
    }

    /**
     * Validate if the status transition is allowed.
     *
     * @param currentStatus The current status of the card.
     * @param newStatus     The target status to transition to.
     * @throws BusinessException If the transition is not allowed.
     */
    private void validateStatusTransition(CardStatus currentStatus, CardStatus newStatus) {
        switch (currentStatus) {
            case CREATED:
                throw new BusinessException("The current status does not allow changes.");
            case ASSIGNED:
                if (newStatus != CardStatus.ACTIVATED && newStatus != CardStatus.DEACTIVATED) {
                    throw new BusinessException("Only ACTIVATED or DEACTIVATED status is allowed from ASSIGNED");
                }
                break;
            case ACTIVATED:
                if (newStatus != CardStatus.DEACTIVATED) {
                    throw new BusinessException("Only DEACTIVATED status is allowed from ACTIVATED");
                }
                break;
            case DEACTIVATED:
                if (newStatus != CardStatus.ACTIVATED) {
                    throw new BusinessException("Only ACTIVATED status is allowed from DEACTIVATED");
                }
                break;
            default:
                throw new BusinessException("Invalid status transition");
        }
    }
}