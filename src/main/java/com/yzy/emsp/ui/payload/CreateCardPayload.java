package com.yzy.emsp.ui.payload;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.yzy.emsp.application.command.CreateAccountCommand;
import com.yzy.emsp.application.command.CreateCardCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel("Card Create Request")
public class CreateCardPayload {


    @ApiModelProperty(value = "Card type", required = true, allowableValues = "0, 1, 2", example = "0")
    private Integer cardType;

    @ApiModelProperty(value = "Initial balance", example = "0.00")
    private BigDecimal balance;

    @ApiModelProperty(value = "Issue date", example = "2025-03-20 15:00:00")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date issueDate;


    public CreateCardCommand toCommand() {

        return CreateCardCommand.of(cardType, balance, issueDate);
    }


}
