package com.yzy.emsp.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yzy.emsp.domain.entity.Account;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.Objects;

@ApiModel("Account View Object")
public class AccountVO {
    @ApiModelProperty(value = "Account ID", example = "1")
    private Integer id;

    @ApiModelProperty(value = "userName", example = "user123")
    private String userName;

    @ApiModelProperty(value = "Contract ID")
    private String contractId;

    @ApiModelProperty(value = "Email", example = "user@example.com")
    private String email;

    @ApiModelProperty(value = "Account Status", example = "Activated")
    private String status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "Creation Time", example = "2023-07-20 15:00:00")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "Update Time", example = "2023-07-20 15:05:00")
    private Date updateTime;


    // 添加静态转换方法
    public static AccountVO fromEntity(Account account) {
        AccountVO vo = new AccountVO();
        BeanUtils.copyProperties(account, vo);
        if (Objects.nonNull(account) && Objects.nonNull(account.getStatus())) {
            vo.setStatus(account.getStatus().getDescription());
        }
        return vo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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