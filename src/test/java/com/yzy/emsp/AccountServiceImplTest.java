package com.yzy.emsp;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yzy.emsp.domain.AccountStatus;
import com.yzy.emsp.domain.dto.AccountDTO;
import com.yzy.emsp.domain.entity.Account;
import com.yzy.emsp.domain.vo.AccountVO;
import com.yzy.emsp.exception.BusinessException;
import com.yzy.emsp.mapper.AccountMapper;
import com.yzy.emsp.service.impl.AccountServiceImpl;
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

@SpringBootTest(classes = {AccountServiceImpl.class})
public class AccountServiceImplTest {

    @Autowired
    private AccountServiceImpl accountService;

    @MockBean
    private AccountMapper accountMapper;


    // ------------------------- Email Format Validation -------------------------
    @Test
    void createAccount_WithInvalidEmailFormat_ShouldThrowException() {

        AccountDTO dto = new AccountDTO();
        dto.setEmail("invalid-email");
        BusinessException ex = assertThrows(BusinessException.class,
            () -> accountService.createAccount(dto));
        assertEquals("Invalid email format", ex.getMessage());
    }

    // ------------------------- EMAID Validation -------------------------
    @Test
    void createAccount_WithInvalidEMAID_ShouldThrowException() {

        when(accountMapper.selectCount(any())).thenReturn(0);

        AccountDTO dto = new AccountDTO();
        dto.setEmail("test@gmail.com");
        dto.setContractId("INVALID_EMAID");

        BusinessException ex = assertThrows(BusinessException.class,
                () -> accountService.createAccount(dto));
        assertEquals("Invalid Contract ID format", ex.getMessage());
    }

    // ------------------------- Account Status Transition -------------------------
    @Test
    void updateStatus_FromInactiveToActive_ShouldSucceed() {
        Account account = new Account();
        account.setStatus(AccountStatus.INACTIVE);
        when(accountMapper.selectById(eq(1))).thenReturn(account);

        AccountVO result = accountService.updateStatus(1, AccountStatus.ACTIVATED);
        assertEquals(AccountStatus.ACTIVATED.getDescription(), result.getStatus());
        verify(accountMapper).updateById(argThat(acc ->
            acc.getStatus() == AccountStatus.ACTIVATED
        ));
    }

    @Test
    void updateStatus_WithInvalidTransition_ShouldThrowException() {
        Account account = new Account();
        account.setStatus(AccountStatus.INACTIVE);
        when(accountMapper.selectById(eq(1))).thenReturn(account);

        BusinessException ex = assertThrows(BusinessException.class,
                () -> accountService.updateStatus(1, AccountStatus.INACTIVE));
        assertTrue(ex.getMessage().contains("Account is already in the target status"));
    }


    // ------------------------- Pagination Query -------------------------
    @Test
    void searchAccountsByUpdateTime_ShouldApplyTimeFilter() {
        IPage<Account> mockPage = new Page<>();
        mockPage.setRecords(Collections.singletonList(new Account()));
        when(accountMapper.selectPage(any(), any())).thenReturn(mockPage);

        Date start = new Date(System.currentTimeMillis() - 10000);
        Date end = new Date();
        PageResult<AccountVO> result = accountService.searchAccountsByUpdateTime(start, end, 1, 10);

        verify(accountMapper).selectPage(
            argThat(page -> page.getCurrent() == 1 && page.getSize() == 10),
            argThat(wrapper -> wrapper.getEntity() == null)
        );
        assertEquals(1, result.getItems().size());
    }
}