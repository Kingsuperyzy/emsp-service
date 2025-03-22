package com.yzy.emsp.domain.model.card;


import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class CardStatusConverter implements AttributeConverter<CardStatus, Integer> {
    @Override
    public Integer convertToDatabaseColumn(CardStatus statusEnum) {
        return statusEnum.getCode();
    }

    @Override
    public CardStatus convertToEntityAttribute(Integer statusCode) {
        return CardStatus.of(statusCode);
    }
}
