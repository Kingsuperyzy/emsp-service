package com.yzy.emsp.domain;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum AccountStatus {

    INACTIVE(0, "Inactive"),
    ACTIVATED(1, "Activated");

    @EnumValue
    private final Integer code;
    private final String description;

    AccountStatus(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @JsonValue
    public Integer getCode() {
        return code;
    }

    @JsonCreator
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


    public static AccountStatus fromString(String status) {
        for (AccountStatus s : AccountStatus.values()) {
            if (s.name().equalsIgnoreCase(status)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Invalid status: " + status);
    }
}