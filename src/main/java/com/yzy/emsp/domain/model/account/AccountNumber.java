package com.yzy.emsp.domain.model.account;

import lombok.*;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Random;

import static com.google.common.base.Preconditions.checkArgument;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountNumber implements Serializable {
    private String value;


    public static AccountNumber of(String value) {



        return new AccountNumber(value);
    }





}
