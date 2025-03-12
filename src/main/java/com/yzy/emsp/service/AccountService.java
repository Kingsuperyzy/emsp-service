package com.yzy.emsp.service;

import com.yzy.emsp.domain.AccountStatus;
import com.yzy.emsp.domain.dto.AccountDTO;

import com.yzy.emsp.domain.vo.AccountVO;
import com.yzy.emsp.utils.PageResult;

import java.util.Date;

public interface AccountService {
    AccountVO createAccount(AccountDTO dto);

    AccountVO updateStatus(Integer id, AccountStatus status);

    PageResult<AccountVO> searchAccountsByUpdateTime(Date startTime,Date endTime, int page, int size);
}
