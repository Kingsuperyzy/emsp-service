package com.yzy.emsp.ui.api;



import com.yzy.emsp.application.AccountService;
import com.yzy.emsp.application.command.CreateAccountCommand;
import com.yzy.emsp.domain.model.account.Account;
import com.yzy.emsp.domain.model.account.AccountStatus;
import com.yzy.emsp.ui.payload.CreateAccountPayload;
import com.yzy.emsp.ui.results.PageResult;
import com.yzy.emsp.ui.results.Result;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/accounts")
@Api(tags = "Account Management")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping
    @ApiOperation("Create Account")
    public Result<Account> createAccount(@RequestBody CreateAccountPayload payload) {
        CreateAccountCommand command = payload.toCommand();
        return Result.success(accountService.createAccount(command));
    }

    @PutMapping("/{accountNo}/status")
    @ApiOperation("Update Account Status")
    public Result<Account> updateStatus(
            @PathVariable String accountNo,
            @RequestParam @ApiParam(value = "Status", allowableValues = "0,1") Integer status) {
        AccountStatus accountStatus = AccountStatus.fromCode(status);
        return Result.success(accountService.updateStatus(accountNo, accountStatus));
    }

    @GetMapping("/search")
    @ApiOperation("Search Accounts by Last Update Time")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime", value = "startTime", required = true, paramType = "query", example = "2025-03-10 15:00:00"),
            @ApiImplicitParam(name = "endTime", value = "endTime", required = true, paramType = "query", example = "2025-03-11 15:00:00"),
            @ApiImplicitParam(name = "page", value = "Page Number", defaultValue = "1", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "Page Size", defaultValue = "10", paramType = "query")
    })
    public Result<PageResult<Account>> searchAccountsByUpdateTime(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startTime,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageResult<Account> result = accountService.searchAccountsByUpdateTime(startTime,endTime, page, size);
        return Result.success(result);

    }
}


