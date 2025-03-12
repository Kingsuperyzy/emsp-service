package com.yzy.emsp.controller;


import com.yzy.emsp.domain.AccountStatus;
import com.yzy.emsp.domain.dto.AccountDTO;
import com.yzy.emsp.domain.vo.AccountVO;
import com.yzy.emsp.service.AccountService;
import com.yzy.emsp.utils.PageResult;
import com.yzy.emsp.utils.Result;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/api/accounts")
@Api(tags = "Account Management")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping
    @ApiOperation("Create Account")
    public Result<AccountVO> createAccount(@Valid @RequestBody AccountDTO dto) {
        return Result.success(accountService.createAccount(dto));
    }

    @PutMapping("/{id}/status")
    @ApiOperation("Update Account Status")
    public Result<AccountVO> updateStatus(
            @PathVariable Integer id,
            @RequestParam @ApiParam(value = "Status", allowableValues = "0,1") Integer status) {
        AccountStatus accountStatus = AccountStatus.fromCode(status);
        return Result.success(accountService.updateStatus(id, accountStatus));
    }

    @GetMapping("/search")
    @ApiOperation("Search Accounts by Last Update Time")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime", value = "startTime", required = true, paramType = "query", example = "2025-03-10 15:00:00"),
            @ApiImplicitParam(name = "endTime", value = "endTime", required = true, paramType = "query", example = "2025-03-11 15:00:00"),
            @ApiImplicitParam(name = "page", value = "Page Number", defaultValue = "1", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "Page Size", defaultValue = "10", paramType = "query")
    })
    public Result<PageResult<AccountVO>> searchAccountsByUpdateTime(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startTime,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageResult<AccountVO> result = accountService.searchAccountsByUpdateTime(startTime,endTime, page, size);
        return Result.success(result);

    }
}


