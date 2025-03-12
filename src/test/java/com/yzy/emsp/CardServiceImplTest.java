package com.yzy.emsp;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yzy.emsp.domain.CardStatus;
import com.yzy.emsp.domain.entity.Account;
import com.yzy.emsp.domain.entity.Card;
import com.yzy.emsp.domain.vo.CardVO;
import com.yzy.emsp.exception.BusinessException;
import com.yzy.emsp.mapper.AccountMapper;
import com.yzy.emsp.mapper.CardMapper;
import com.yzy.emsp.service.impl.CardServiceImpl;
import com.yzy.emsp.utils.PageResult;
import org.junit.jupiter.api.Test;
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
        assertTrue(ex.getMessage().contains("ASSIGNED"));
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
        IPage<Card> mockPage = new Page<>();
        mockPage.setRecords(Collections.singletonList(new Card()));
        when(cardMapper.selectPage(any(), any())).thenReturn(mockPage);

        Date start = new Date(System.currentTimeMillis() - 10000);
        PageResult<CardVO> result = cardService.searchByUpdateTime(start, null, 1, 10);

        verify(cardMapper).selectPage(
            argThat(page -> page.getCurrent() == 1),
            argThat(wrapper -> wrapper.getEntity() == null)
        );
        assertEquals(1, result.getItems().size());
    }


}