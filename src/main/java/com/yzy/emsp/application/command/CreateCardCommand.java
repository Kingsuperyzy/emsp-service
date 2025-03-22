package com.yzy.emsp.application.command;


import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;


@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateCardCommand {

    private Integer cardType;

    private BigDecimal balance;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date issueDate;

    public static CreateCardCommand of(Integer cardType, BigDecimal balance, Date issueDate) {

        return new CreateCardCommand(cardType, balance, issueDate);
    }


}
