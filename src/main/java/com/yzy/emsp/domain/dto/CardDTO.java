package com.yzy.emsp.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yzy.emsp.domain.CardType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel("Card Create Request")
public class CardDTO {
    /*@ApiModelProperty(value = "Card number(Visible Number)", required = true, example = "EV-202503-000001")
    private String cardNumber;

    @ApiModelProperty(value = "Unique Identifier", example = "3ACE497FFDE4DBE8")
    private String uid;*/

    @ApiModelProperty(value = "Card type", required = true, allowableValues = "0, 1, 2", example = "0")
    @NotNull(message = "Card type cannot be null")
    private CardType cardType;

    @ApiModelProperty(value = "Initial balance", example = "0.00")
    @DecimalMin(value = "0.00", message = "Balance cannot be negative")
    @Digits(integer = 10, fraction = 2, message = "Invalid balance format")
    private BigDecimal balance;

    @ApiModelProperty(value = "Issue date", example = "2023-07-20 15:00:00")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date issueDate;

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

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }
}