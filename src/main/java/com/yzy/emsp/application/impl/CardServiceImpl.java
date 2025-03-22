package com.yzy.emsp.application.impl;


import com.yzy.emsp.application.CardService;
import com.yzy.emsp.application.command.CreateCardCommand;
import com.yzy.emsp.domain.model.account.Account;
import com.yzy.emsp.domain.model.card.*;
import com.yzy.emsp.exception.BusinessException;
import com.yzy.emsp.ui.results.PageResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;


@Service
public class CardServiceImpl implements CardService {


    private CardRepository cardRepository;

    private CardManagement cardManagement;

    public CardServiceImpl(CardRepository cardRepository,CardManagement cardManagement) {
        this.cardRepository = cardRepository;
        this.cardManagement = cardManagement;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Card createCard(CreateCardCommand cardCommand) {


        Card card = Card.of(cardCommand);
        cardRepository.save(card);

        return card;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Card assignCard(String cardNo, String userNo) {

        CardNumber cN = CardNumber.of(cardNo);
        Card card = cardRepository.findByCardNo(cN);
        if (card == null) {
            throw new BusinessException("Card not found");
        }
        Account account = cardManagement.findByAccountNo(userNo);
        if (account == null) {
            throw new BusinessException("user not found");
        }

        if (card.getStatus() != CardStatus.CREATED) {
            throw new BusinessException("Card cannot be assigned in current status");
        }
        card.assign(userNo);
        cardRepository.update(card);
        return card;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Card updateCardStatus(String cardNo, CardStatus newStatus) {
        // 1. Check if the card exists
        CardNumber cN = CardNumber.of(cardNo);
        Card card = cardRepository.findByCardNo(cN);
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
        if (newStatus == CardStatus.DEACTIVATED) {
            card.deactivated();
        } else {
            card.activated();
        }

        cardRepository.update(card);

        // 5. Return the updated card information
        return card;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public PageResult<Card> searchByUpdateTime(Date startTime, Date endTime, int page, int size) {

        List<Card> cardList = cardRepository.findByCreateTimeBetween(startTime, endTime, page, size);
        long total = cardRepository.countByCreateTimeBetween(startTime, endTime);
        return new PageResult<>(
                cardList,
                total,
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