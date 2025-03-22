package com.yzy.emsp.ui.api;



import com.yzy.emsp.application.CardService;
import com.yzy.emsp.application.command.AssignCardCommand;
import com.yzy.emsp.application.command.CreateCardCommand;
import com.yzy.emsp.domain.model.card.Card;
import com.yzy.emsp.domain.model.card.CardStatus;
import com.yzy.emsp.ui.payload.AssignCardPayload;
import com.yzy.emsp.ui.payload.CreateCardPayload;
import com.yzy.emsp.ui.results.PageResult;
import com.yzy.emsp.ui.results.Result;
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
    public Result<Card> createCard(@Valid @RequestBody CreateCardPayload payload) {
        CreateCardCommand createCardCommand = payload.toCommand();
        return Result.success(cardService.createCard(createCardCommand));
    }

    @PutMapping("/assign")
    @ApiOperation("Assign Card to User")
    public Result assignCard(@Valid @RequestBody AssignCardPayload payload) {
        AssignCardCommand assignCardCommand = payload.toCommand();
        return  Result.success(cardService.assignCard(assignCardCommand.getCardNo(), assignCardCommand.getUserNo()));
    }

    @PutMapping("/{cardNo}/status")
    @ApiOperation("Update  Card Status")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cardNo", value = "Card No", required = true, paramType = "path"),
            @ApiImplicitParam(name = "status", value = "Target Status", required = true, paramType = "query")
    })
    public Result<Card> updateCardStatus(
            @PathVariable String cardNo,
            @RequestParam @ApiParam(value = "Status", allowableValues = "0, 1, 2, 3") Integer status) {
        CardStatus cardStatus = CardStatus.fromCode(status);
        Card cardVO = cardService.updateCardStatus(cardNo, cardStatus);
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
    public Result<PageResult<Card>> searchAccountsByUpdateTime(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startTime,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageResult<Card> result = cardService.searchByUpdateTime(startTime,endTime, page, size);
        return Result.success(result);
    }
}