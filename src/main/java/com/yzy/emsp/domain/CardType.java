package com.yzy.emsp.domain;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;

@ApiModel("Card Type")
public enum CardType {
    RFID(0, "RFID"),
    NFC(1, "NFC"),
    QR_CODE(2, "QR Code");

    @EnumValue
    private final Integer code;
    private final String description;

    CardType(Integer code, String description) {
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
    public static CardType fromCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (CardType type : CardType.values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid CardType code: " + code);
    }


}