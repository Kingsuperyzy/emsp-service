package com.yzy.emsp;

import com.yzy.emsp.domain.AccountStatus;
import com.yzy.emsp.domain.dto.AccountDTO;
import com.yzy.emsp.domain.vo.AccountVO;
import com.yzy.emsp.domain.vo.CardVO;
import com.yzy.emsp.service.AccountService;
import com.yzy.emsp.utils.PageResult;
import com.yzy.emsp.utils.Result;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @Test
    public void testCreateAccount() throws Exception {
        AccountDTO accountDTO = new AccountDTO();
        AccountVO accountVO = new AccountVO();
       // when(accountService.createAccount(any(AccountDTO.class))).thenReturn(accountVO);

        mockMvc.perform(post("/api/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"contractId\":\"DE123ABC4567890\",\"email\":\"123@qq.com\",\"password\":\"123@ABC\"," +
                        "\"userName\":\"abc\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("200"));
    }

    @Test
    public void testUpdateStatus() throws Exception {
        AccountVO accountVO = new AccountVO();
        //when(accountService.updateStatus(anyInt(), any(AccountStatus.class))).thenReturn(accountVO);

        mockMvc.perform(put("/api/accounts/1/status")
                .param("status", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("200"));
    }

    @Test
    public void testSearchAccountsByUpdateTime() throws Exception {
        PageResult<AccountVO> pageResult = new PageResult<>(null, 0, 1, 10);
        //when(accountService.searchAccountsByUpdateTime(any(Date.class), any(Date.class), anyInt(), anyInt())).thenReturn(pageResult);

        mockMvc.perform(get("/api/accounts/search")
                .param("startTime", "2025-03-10 15:00:00")
                .param("endTime", "2025-03-11 15:00:00")
                .param("page", "1")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("200"));
    }
}