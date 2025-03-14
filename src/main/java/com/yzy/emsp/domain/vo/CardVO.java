package com.yzy.emsp.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yzy.emsp.domain.entity.Card;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@ApiModel(" Card View Object")
public class CardVO {
    @ApiModelProperty(value = "Card ID", example = "1")
    private Integer id;

    @ApiModelProperty(value = "Visible Number", example = "EV-YYYYMM-000001")
    private String cardNumber;

    @ApiModelProperty(value = "Unique Identifier", example = "550e8400-e29b-41d4-a716-446655440000")
    private String uid;

    @ApiModelProperty(value = "Bound User ID", example = "123")
    private Integer userId;

    @ApiModelProperty(value = "Card Type", example = "RFID")
    private String cardType;

    @ApiModelProperty(value = "Balance", example = "100.00")
    private BigDecimal balance;

    @ApiModelProperty(value = "Card Status", example = "Assigned")
    private String status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "Issue Date", example = "2023-07-20 15:00:00")
    private Date issueDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "Creation Time", example = "2023-07-20 15:00:00")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "Update Time", example = "2023-07-20 15:05:00")
    private Date updateTime;

    @ApiModelProperty("Bound User Name")
    private Integer userName;

    @ApiModelProperty("Contract ID")
    private String contractId;


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

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    public Integer getUserName() {
        return userName;
    }

    public void setUserName(Integer userName) {
        this.userName = userName;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    // 添加静态转换方法
    public static CardVO fromEntity(Card card) {
        CardVO vo = new CardVO();
        BeanUtils.copyProperties(card, vo);
        if (Objects.nonNull(card) && Objects.nonNull(card.getStatus())) {
            vo.setStatus(card.getStatus().getDescription());
        }
        if (Objects.nonNull(card) && Objects.nonNull(card.getCardType())) {
            vo.setCardType(card.getCardType().getDescription());
        }
        return vo;
    }
}