package com.yzy.emsp.domain.model.card;


import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class CardTypeConverter implements AttributeConverter<CardType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(CardType typeEnum) {
        return typeEnum.getCode();
    }

    @Override
    public CardType convertToEntityAttribute(Integer typeCode) {
        return CardType.of(typeCode);
    }
}
