package com.yzy.emsp.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yzy.emsp.domain.AccountStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;


@ApiModel("Account Entity")
@TableName("ev_account")
public class Account {
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "Account ID", example = "1")
    private Integer id;

    @TableField("user_name")
    @ApiModelProperty(value = "userName", required = true, example = "user123")
    @NotBlank(message = "Username cannot be empty")
    private String userName;

    @TableField("password")
    @ApiModelProperty(value = "Password", required = true, example = "password123")
    @NotBlank(message = "Password cannot be empty")
    @Size(min = 12, message = "Password must be at least 12 characters")
    private String password;

    @TableField("contract_id")
    @ApiModelProperty("Contract ID")
    private String contractId;

    @TableField("email")
    @Email(message = "Invalid email format")
    @ApiModelProperty(value = "Email", required = true, example = "user@example.com")
    @NotBlank(message = "Email cannot be empty")
    private String email;


    @TableField(value = "status")
    @ApiModelProperty(value = "Account Status")
    private AccountStatus status;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "Creation Time", example = "2023-07-20 15:00:00")
    private Date createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "Update Time", example = "2023-07-20 15:05:00")
    private Date updateTime;

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}

