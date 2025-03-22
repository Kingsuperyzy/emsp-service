package com.yzy.emsp.domain.model.card;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.base.Objects;

import com.yzy.emsp.application.command.CreateCardCommand;


import com.yzy.emsp.domain.model.account.AccountStatus;
import com.yzy.emsp.utils.EMAIDUtil;
import com.yzy.emsp.utils.RFIDCardUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;

import static com.google.common.base.Preconditions.checkArgument;

@Entity
@ApiModel(" Card Entity")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "Card ID", example = "1")
    private Integer id;

    @Convert(converter = CardNumberConverter.class)
    @Column(name = "card_no", length = 32, nullable = false, unique = true)
    @ApiModelProperty(value = "Card Number", required = true, example = "100001")
    private CardNumber cardNo;



    @Column(name = "uid", length = 64, nullable = false)
    @ApiModelProperty(value = "Unique Identifier", example = "550e8400-e29b-41d4-a716-446655440000")
    private String uid;


    @Column(name = "user_no", length = 64)
    @ApiModelProperty("Bound User No")
    private String userNo;


    @Column(name = "card_type", nullable = false)
    @Convert(converter = CardTypeConverter.class)
    @ApiModelProperty(value = "Card Type")
    private CardType cardType;


    @ApiModelProperty("Card Balance")
    @Column(name = "balance", nullable = false, precision = 10, scale = 2)
    private BigDecimal balance;


    @Column(name = "status", nullable = false)
    @Convert(converter = CardStatusConverter.class)
    @ApiModelProperty(value = "Card Status")
    private CardStatus status;

    @Column(name = "issue_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "Issue Date", example = "2023-07-20 15:00:00")
    private Date issueDate;

    @Column(name = "create_time", insertable = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "Creation Time", example = "2023-07-20 15:00:00")
    private Date createTime;

    @Column(name = "update_time", updatable = true )
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "Update Time", example = "2023-07-20 15:05:00")
    private Date updateTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Card card = (Card) o;
        return Objects.equal(cardNo, card.cardNo);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), cardNo);
    }

    public static Card of(CreateCardCommand command) {

        checkArgument(!StringUtils.isEmpty(command.getBalance()), "Balance cannot be empty");
        checkArgument(command.getBalance().compareTo(BigDecimal.ZERO) > 0, "Balance cannot be negative");
        checkArgument(!StringUtils.isEmpty(command.getCardType()), "CardType cannot be empty");
        String uid = RFIDCardUtil.generateUID();
        CardNumber newCardNo = CardNumber.of(RFIDCardUtil.generateVisibleId());
        CardStatus defaultCardStatus = CardStatus.CREATED;
        CardType cardType = CardType.fromCode(command.getCardType());
        if (command.getIssueDate() == null) {
            Card card = new Card(null, newCardNo, uid,null,
                    cardType,command.getBalance(),defaultCardStatus,new Date(),
                    new Date(),new Date());
            return card;
        }

        Card card = new Card(null, newCardNo, uid,null,
                cardType,command.getBalance(),defaultCardStatus,command.getIssueDate(),
                new Date(),new Date());
        return card;
    }

    public void assign(String userNo) {
        this.userNo = userNo;
        this.status = CardStatus.ASSIGNED;
        this.updateTime= new Date();
    }

    public void activated() {

        this.status = CardStatus.ACTIVATED;
        this.updateTime= new Date();
    }

    public void deactivated() {
        this.status = CardStatus.DEACTIVATED;
        this.updateTime= new Date();
    }
}
