package com.yzy.emsp.domain.model.account;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class AccountStatusConverter implements AttributeConverter<AccountStatus, Integer> {
    @Override
    public Integer convertToDatabaseColumn(AccountStatus statusEnum) {
        return statusEnum.getCode();
    }

    @Override
    public AccountStatus convertToEntityAttribute(Integer statusCode) {
        return AccountStatus.of(statusCode);
    }
}
