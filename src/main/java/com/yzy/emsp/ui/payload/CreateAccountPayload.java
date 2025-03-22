package com.yzy.emsp.ui.payload;


import com.yzy.emsp.application.command.CreateAccountCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@ApiModel("Account Create Request")
public class CreateAccountPayload {
    @ApiModelProperty(value = "userName", required = true)
    private String userName;

    @ApiModelProperty(value = "Password", required = true)
    private String password;

    @ApiModelProperty("Contract ID")
    private String contractId;

    @ApiModelProperty(value = "Email", required = true)
    private String email;


    public CreateAccountCommand toCommand() {

        return CreateAccountCommand.of(userName, password, contractId, email);
    }


}
