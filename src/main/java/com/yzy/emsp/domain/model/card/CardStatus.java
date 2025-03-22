package com.yzy.emsp.domain.model.card;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum CardStatus {
    CREATED(0, "Created"),
    ASSIGNED(1, "Assigned"),
    ACTIVATED(2, "Activated"),
    DEACTIVATED(3, "Deactivated");

    @Getter
    @JsonValue
    private final Integer code;

    @Getter
    private final String description;


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

    public static CardStatus of(Integer code) {
        CardStatus[] values = CardStatus.values();
        for (CardStatus val : values) {
            if (val.getCode().equals(code)) {
                return val;
            }
        }
        throw new IllegalArgumentException(String.format("Invalid AccountStatus code: ", code));
    }


}