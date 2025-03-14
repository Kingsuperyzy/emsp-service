package com.yzy.emsp.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel("Account Create Request")
public class AccountDTO {
    @ApiModelProperty(value = "UserName", required = true)
    private String UserName;

    @ApiModelProperty(value = "Password", required = true)
    private String password;

    @ApiModelProperty("Contract ID")
    private String contractId;

    @ApiModelProperty(value = "Email", required = true)
    private String email;

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}