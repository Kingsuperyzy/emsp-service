package com.yzy.emsp.domain.model.account;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class EMAIDConverter implements AttributeConverter<EMAID, String> {
    @Override
    public String convertToDatabaseColumn(EMAID emaid) {
        return emaid.getValue();
    }

    @Override
    public EMAID convertToEntityAttribute(String value) {
        return EMAID.of(value);
    }
}
