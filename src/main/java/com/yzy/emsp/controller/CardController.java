package com.yzy.emsp.controller;


import com.yzy.emsp.domain.CardStatus;
import com.yzy.emsp.domain.dto.CardAssignDTO;
import com.yzy.emsp.domain.dto.CardDTO;
import com.yzy.emsp.domain.vo.CardVO;
import com.yzy.emsp.service.CardService;
import com.yzy.emsp.utils.PageResult;
import com.yzy.emsp.utils.Result;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

// CardController.java
@RestController
@RequestMapping("/api/cards")
@Api(tags = " Card Management")
public class CardController {

    @Autowired
    private CardService cardService;

    @PostMapping
    @ApiOperation("Create  Card")
    public Result<CardVO> createCard(@Valid @RequestBody CardDTO dto) {
        return Result.success(cardService.createCard(dto));
    }

    @PutMapping("/assign")
    @ApiOperation("Assign Card to User")
    public Result assignCard(@Valid @RequestBody CardAssignDTO dto) {
        return  Result.success(cardService.assignCard(dto.getCardId(), dto.getUserId()));
    }

    @PutMapping("/{cardId}/status")
    @ApiOperation("Update  Card Status")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cardId", value = "Card ID", required = true, paramType = "path"),
            @ApiImplicitParam(name = "status", value = "Target Status", required = true, paramType = "query")
    })
    public Result<CardVO> updateCardStatus(
            @PathVariable Integer cardId,
            @RequestParam @ApiParam(value = "Status", allowableValues = "0, 1, 2, 3") Integer status) {
        CardStatus cardStatus = CardStatus.fromCode(status);
        CardVO cardVO = cardService.updateCardStatus(cardId, cardStatus);
        return Result.success(cardVO);
    }

    @GetMapping("/search")
    @ApiOperation("Search Accounts by Last Update Time")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime", value = "startTime", required = true, paramType = "query", example = "2025-03-10 15:00:00"),
            @ApiImplicitParam(name = "endTime", value = "endTime", required = true, paramType = "query", example = "2025-03-11 15:00:00"),
            @ApiImplicitParam(name = "page", value = "Page Number", defaultValue = "1", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "Page Size", defaultValue = "10", paramType = "query")
    })
    public Result<PageResult<CardVO>> searchAccountsByUpdateTime(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startTime,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageResult<CardVO> result = cardService.searchByUpdateTime(startTime,endTime, page, size);
        return Result.success(result);
    }
}