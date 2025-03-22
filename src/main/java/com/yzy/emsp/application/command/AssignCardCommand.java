package com.yzy.emsp.application.command;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;


@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AssignCardCommand {


    private String userNo;

    private String cardNo;

    public static AssignCardCommand of(String userNo, String cardNo) {

        return new AssignCardCommand(userNo, cardNo);
    }


}
