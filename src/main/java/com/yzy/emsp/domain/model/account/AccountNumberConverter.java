package com.yzy.emsp.domain.model.account;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class AccountNumberConverter implements AttributeConverter<AccountNumber, String> {
    @Override
    public String convertToDatabaseColumn(AccountNumber accountNumber) {
        return accountNumber.getValue();
    }

    @Override
    public AccountNumber convertToEntityAttribute(String value) {
        return AccountNumber.of(value);
    }
}
