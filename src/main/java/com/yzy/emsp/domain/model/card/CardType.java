package com.yzy.emsp.domain.model.card;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum CardType {
    RFID(0, "RFID"),
    NFC(1, "NFC"),
    QR_CODE(2, "QR Code");

    @Getter
    @JsonValue
    private final Integer code;

    @Getter
    private final String description;



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

    public static CardType of(Integer code) {
        CardType[] values = CardType.values();
        for (CardType val : values) {
            if (val.getCode().equals(code)) {
                return val;
            }
        }
        throw new IllegalArgumentException(String.format("Invalid AccountStatus code: ", code));
    }


}