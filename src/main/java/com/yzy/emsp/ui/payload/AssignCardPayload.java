package com.yzy.emsp.ui.payload;


import com.yzy.emsp.application.command.AssignCardCommand;
import com.yzy.emsp.application.command.CreateAccountCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;


@Data
@ApiModel("Card Assign Request")
public class AssignCardPayload {

    @ApiModelProperty(value = "userNo", required = true)
    private String userNo;

    @ApiModelProperty(value = "cardNo", required = true)
    private String cardNo;

    public AssignCardCommand toCommand() {

        return AssignCardCommand.of(userNo, cardNo);
    }


}
