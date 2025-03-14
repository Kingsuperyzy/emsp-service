package com.yzy.emsp;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yzy.emsp.domain.CardStatus;
import com.yzy.emsp.domain.entity.Account;
import com.yzy.emsp.domain.entity.Card;
import com.yzy.emsp.domain.query.CardQuery;
import com.yzy.emsp.domain.vo.CardVO;
import com.yzy.emsp.exception.BusinessException;
import com.yzy.emsp.mapper.AccountMapper;
import com.yzy.emsp.mapper.CardMapper;
import com.yzy.emsp.service.impl.CardServiceImpl;
import com.yzy.emsp.utils.PageResult;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {CardServiceImpl.class})
public class CardServiceImplTest {

    @Autowired
    private CardServiceImpl cardService;

    @MockBean
    private CardMapper cardMapper;

    @MockBean
    private AccountMapper accountMapper;

    // ------------------------- Card Status Transition -------------------------
    @Test
    void updateCardStatus_FromAssignedToActivated_ShouldSucceed() {
        Card card = new Card();
        card.setStatus(CardStatus.ASSIGNED);
        when(cardMapper.selectById(eq(1))).thenReturn(card);

        CardVO result = cardService.updateCardStatus(1, CardStatus.ACTIVATED);
        assertEquals(CardStatus.ACTIVATED.getDescription(), result.getStatus());
        verify(cardMapper).updateById(argThat(c ->
            c.getStatus() == CardStatus.ACTIVATED
        ));
    }

    @Test
    void updateCardStatus_WithInvalidTransition_ShouldThrowException() {
        Card card = new Card();
        card.setStatus(CardStatus.CREATED);
        when(cardMapper.selectById(eq(1))).thenReturn(card);

        BusinessException ex = assertThrows(BusinessException.class,
            () -> cardService.updateCardStatus(1, CardStatus.DEACTIVATED));
        assertTrue(ex.getMessage().equals("The current status does not allow changes."));
    }

    // ------------------------- Card Assignment -------------------------
    @Test
    void assignCard_WithValidUser_ShouldUpdateStatus() {
        Card card = new Card();
        Account account = new Account();
        account.setId(100);
        card.setStatus(CardStatus.CREATED);
        when(cardMapper.selectById(eq(1))).thenReturn(card);
        when(accountMapper.selectById(eq(100))).thenReturn(account);
        assertTrue(cardService.assignCard(1, 100));
        verify(cardMapper).updateById(argThat(c ->
            c.getStatus() == CardStatus.ASSIGNED && c.getUserId() == 100
        ));
    }

    @Test
    void assignCard_WithInvalidUser_ShouldThrowException() {
        when(cardMapper.selectById(eq(1))).thenReturn(new Card());
        when(accountMapper.selectById(eq(100))).thenReturn(null);

        BusinessException ex = assertThrows(BusinessException.class,
            () -> cardService.assignCard(1, 100));
        assertEquals("user not found", ex.getMessage());
    }

    // ------------------------- Pagination Query -------------------------
    @Test
    void searchByUpdateTime_WithStartTimeOnly_ShouldApplyFilter() {
        // 1. Mock data preparation
        IPage<Card> mockPage = new Page<>();
        mockPage.setRecords(Collections.singletonList(new Card()));
        mockPage.setTotal(1L);

        // 2. Argument captor
        ArgumentCaptor<Page<Card>> pageCaptor = ArgumentCaptor.forClass(Page.class);
        ArgumentCaptor<CardQuery> queryCaptor = ArgumentCaptor.forClass(CardQuery.class);

        // 3. Set up mock behavior
        when(cardMapper.selectCardPage(any(Page.class), any(CardQuery.class))).thenReturn(mockPage);

        // 4. Execute test
        Date startTime = new Date(System.currentTimeMillis() - 10000);
        PageResult<CardVO> result = cardService.searchByUpdateTime(startTime, null, 1, 10);

        // 5. Verify invocation arguments
        verify(cardMapper).selectCardPage(pageCaptor.capture(), queryCaptor.capture());

        // Verify pagination parameters
        Page<Card> capturedPage = pageCaptor.getValue();
        assertAll(
                () -> assertEquals(1, capturedPage.getCurrent()),
                () -> assertEquals(10, capturedPage.getSize())
        );

        // Verify query conditions
        CardQuery capturedQuery = queryCaptor.getValue();
        assertAll(
                () -> assertEquals(startTime, capturedQuery.getStartTime()),
                () -> assertNull(capturedQuery.getEndTime())
        );

        // 6. Verify result
        assertEquals(1, result.getItems().size());
        assertEquals(1, result.getTotalItems());
    }


}