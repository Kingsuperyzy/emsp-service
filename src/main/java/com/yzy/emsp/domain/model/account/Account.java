package com.yzy.emsp.domain.model.account;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.base.Objects;
import com.yzy.emsp.application.command.CreateAccountCommand;
import com.yzy.emsp.utils.EMAIDUtil;
import com.yzy.emsp.utils.EmailValidator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;

import static com.google.common.base.Preconditions.checkArgument;


@Entity
@ApiModel("Account Entity")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "Account ID", example = "1")
    private Integer id;

    @Convert(converter = AccountNumberConverter.class)
    @Column(name = "account_no", length = 32, nullable = false, unique = true)
    private AccountNumber accountNo;


    @Column(name = "user_name", length = 64, nullable = false)
    @ApiModelProperty(value = "userName", required = true, example = "user123")
    private String userName;

    @Column(name = "password", length = 64, nullable = false)
    @ApiModelProperty(value = "Password", required = true, example = "password123")
    private String password;


    @Column(name = "contract_id", length = 64, nullable = false)
    @ApiModelProperty("Contract ID")
    @Convert(converter = EMAIDConverter.class)
    private EMAID contractId;

    @Column(name = "email", length = 64, nullable = false)
    @ApiModelProperty(value = "Email", required = true, example = "user@example.com")
    private String email;


    @Convert(converter = AccountStatusConverter.class)
    @Column(name = "status", nullable = false)
    @ApiModelProperty(value = "Account Status")
    private AccountStatus status;


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
        Account product = (Account) o;
        return Objects.equal(accountNo, product.accountNo);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), accountNo);
    }

    public static Account of(CreateAccountCommand command) {



        checkArgument(!StringUtils.isEmpty(command.getUserName()), "username cannot be empty");
        checkArgument(!StringUtils.isEmpty(command.getPassword()), "password cannot be empty");
        checkArgument(command.getPassword().length()<12, "password must be at least 12 characters");
        checkArgument(!StringUtils.isEmpty(command.getEmail()), "email cannot be empty");
        checkArgument(EmailValidator.isValidEmail(command.getEmail()), "Invalid email format");

        long timestamp = Instant.now().toEpochMilli();
        AccountNumber newAccountNo = AccountNumber.of(timestamp + "-" + command.getUserName());
        AccountStatus defaultAccountStatus = AccountStatus.INACTIVE;
        String contractId = command.getContractId();

        if (StringUtils.isEmpty(command.getContractId())) {
            EMAID emaid = EMAID.generate(false);
            Account account = new Account(null, newAccountNo, command.getUserName(), command.getPassword(),
                    emaid,command.getEmail(), defaultAccountStatus,new Date(),new Date());
            return account;
        }
        checkArgument(EMAIDUtil.isValidEMAID(command.getContractId()), "Invalid EMAID format");
        Account account = new Account(null, newAccountNo, command.getUserName(), command.getPassword(),
                EMAID.of(contractId),command.getEmail(), defaultAccountStatus,new Date(),new Date());

        return account;
    }

    public void activated() {

        this.status = AccountStatus.ACTIVATED;
        this.updateTime= new Date();
    }

    public void deactivated() {
        this.status = AccountStatus.INACTIVE;
        this.updateTime= new Date();
    }



}

