package com.yzy.emsp.domain;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;

@ApiModel("Card Status")
public enum CardStatus {
    CREATED(0, "Created"),
    ASSIGNED(1, "Assigned"),
    ACTIVATED(2, "Activated"),
    DEACTIVATED(3, "Deactivated");

    @EnumValue
    private final Integer code;
    private final String description;

    CardStatus(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    @JsonValue
    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    @JsonCreator
    public static CardStatus fromCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (CardStatus status : CardStatus.values()) {
            if (status.code.equals(code)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid CardStatus code: " + code);
    }


}