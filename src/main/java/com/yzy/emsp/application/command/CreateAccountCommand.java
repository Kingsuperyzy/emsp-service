package com.yzy.emsp.application.command;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateAccountCommand {
    private String userName;

    private String password;

    private String contractId;

    private String email;

    public static CreateAccountCommand of(String userName, String password, String contractId, String email) {

        return new CreateAccountCommand(userName, password, contractId, email);
    }


}
