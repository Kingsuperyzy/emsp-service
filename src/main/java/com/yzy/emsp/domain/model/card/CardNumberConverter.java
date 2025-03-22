package com.yzy.emsp.domain.model.card;

import com.yzy.emsp.domain.model.account.AccountNumber;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class CardNumberConverter implements AttributeConverter<CardNumber, String> {
    @Override
    public String convertToDatabaseColumn(CardNumber cardNumber) {
        return cardNumber.getValue();
    }

    @Override
    public CardNumber convertToEntityAttribute(String value) {
        return CardNumber.of(value);
    }
}
