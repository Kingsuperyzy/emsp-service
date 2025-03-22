package com.yzy.emsp.domain.model.account;


import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;



@AllArgsConstructor
public enum AccountStatus {

    INACTIVE(0, "Inactive"),
    ACTIVATED(1, "Activated");

    @Getter
    @JsonValue
    private final Integer code;

    @Getter
    private final String description;

    public static AccountStatus fromCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (AccountStatus status : AccountStatus.values()) {
            if (status.code.equals(code)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid AccountStatus code: " + code);
    }


    public static AccountStatus of(Integer code) {
        AccountStatus[] values = AccountStatus.values();
        for (AccountStatus val : values) {
            if (val.getCode().equals(code)) {
                return val;
            }
        }
        throw new IllegalArgumentException(String.format("Invalid AccountStatus code: ", code));
    }
}