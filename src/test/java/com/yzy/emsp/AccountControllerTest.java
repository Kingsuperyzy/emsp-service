package com.yzy.emsp;


import com.yzy.emsp.application.AccountService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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

        mockMvc.perform(post("/api/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"contractId\":\"DE123ABC4567890\",\"email\":\"123@qq.com\",\"password\":\"123@ABC\"," +
                        "\"userName\":\"abc\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("200"));
    }

    @Test
    public void testUpdateStatus() throws Exception {


        mockMvc.perform(put("/api/accounts/1742601692549-abc/status")
                .param("status", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("200"));
    }

    @Test
    public void testSearchAccountsByUpdateTime() throws Exception {


        mockMvc.perform(get("/api/accounts/search")
                .param("startTime", "2025-03-10 15:00:00")
                .param("endTime", "2025-03-22 15:00:00")
                .param("page", "1")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("200"));
    }
}
