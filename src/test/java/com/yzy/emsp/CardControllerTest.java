package com.yzy.emsp.controller;

import com.yzy.emsp.domain.CardStatus;
import com.yzy.emsp.domain.CardType;
import com.yzy.emsp.domain.dto.CardAssignDTO;
import com.yzy.emsp.domain.dto.CardDTO;
import com.yzy.emsp.domain.vo.CardVO;
import com.yzy.emsp.service.CardService;
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
public class CardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CardService cardService;

    @Test
    public void testCreateCard() throws Exception {

        //when(cardService.createCard(any(CardDTO.class))).thenReturn(cardVO);

        mockMvc.perform(post("/api/cards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"balance\":0,\"cardType\":0,\"issueDate\":\"2023-07-20 15:00:00\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("200"));
    }

    @Test
    public void testAssignCard() throws Exception {

       // when(cardService.assignCard(anyInt(), anyInt())).thenReturn(true);

        mockMvc.perform(put("/api/cards/assign")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"cardId\":1,\"userId\":1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("Operation succeeded"));
    }

    @Test
    public void testUpdateCardStatus() throws Exception {
       // CardVO cardVO = new CardVO();
        //when(cardService.updateCardStatus(anyInt(), any(CardStatus.class))).thenReturn(cardVO);

        mockMvc.perform(put("/api/cards/1/status")
                        .param("status", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200));
    }

    @Test
    public void testSearchAccountsByUpdateTime() throws Exception {
       // PageResult<CardVO> pageResult = new PageResult<>(null, 0, 1, 10);
        //when(cardService.searchByUpdateTime(any(Date.class), any(Date.class), anyInt(), anyInt())).thenReturn(pageResult);

        mockMvc.perform(get("/api/cards/search")
                        .param("startTime", "2025-03-10 15:00:00")
                        .param("endTime", "2025-03-11 15:00:00")
                        .param("page", "1")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200));
    }
}