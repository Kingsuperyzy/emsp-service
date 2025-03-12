package com.yzy.emsp.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel("Account Create Request")
public class AccountDTO {
    @ApiModelProperty(value = "Username", required = true)
    private String username;

    @ApiModelProperty(value = "Password", required = true)
    private String password;

    @ApiModelProperty("Contract ID")
    private String contractId;

    @ApiModelProperty(value = "Email", required = true)
    private String email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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