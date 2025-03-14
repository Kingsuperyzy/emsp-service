package com.yzy.emsp.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yzy.emsp.domain.CardStatus;
import com.yzy.emsp.domain.CardType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;


@ApiModel(" Card Entity")
@TableName("ev_card")
public class Card {
    @Id
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "Card ID", example = "1")
    private Integer id;

    @TableField("card_number")
    @ApiModelProperty(value = "Card Number", required = true, example = "100001")
    @NotNull(message = "Card number cannot be null")
    private String cardNumber;

    @TableField("uid")
    @ApiModelProperty(value = "Unique Identifier", example = "550e8400-e29b-41d4-a716-446655440000")
    private String uid;

    @TableField("user_id")
    @ApiModelProperty("Bound User ID")
    private Integer userId;

    @TableField(exist = false)
    @ApiModelProperty("Bound User Name")
    private String userName;

    @TableField(exist = false)
    @ApiModelProperty("Contract ID")
    private String contractId;


    @TableField(value = "card_type")
    @ApiModelProperty(value = "Card Type")
    private CardType cardType;

    @TableField("balance")
    @ApiModelProperty("Card Balance")
    private BigDecimal balance;


    @TableField(value = "status")
    @ApiModelProperty(value = "Card Status")
    private CardStatus status;

    @TableField("issue_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "Issue Date", example = "2023-07-20 15:00:00")
    private Date issueDate;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "Creation Time", example = "2023-07-20 15:00:00")
    private Date createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "Update Time", example = "2023-07-20 15:05:00")
    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public CardStatus getStatus() {
        return status;
    }

    public void setStatus(CardStatus status) {
        this.status = status;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
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
}