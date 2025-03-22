package com.yzy.emsp.domain.model.card;

import lombok.*;

import java.io.Serializable;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CardNumber implements Serializable {
    private String value;


    public static CardNumber of(String value) {



        return new CardNumber(value);
    }





}
