package com.yzy.emsp.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;


@ApiModel("Card Assign Request")
public class CardAssignDTO {
    @ApiModelProperty(value = "User ID", required = true)
    @NotNull(message = "User ID cannot be null")
    private Integer userId;

    @ApiModelProperty(value = "Card ID", required = true)
    @NotNull(message = "Card ID cannot be null")
    private Integer cardId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCardId() {
        return cardId;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }
}