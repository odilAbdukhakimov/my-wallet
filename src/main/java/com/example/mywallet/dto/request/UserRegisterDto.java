package com.example.mywallet.dto.request;

import lombok.Data;

@Data
public class UserRegisterDto {

    private String name;
    private String username;
    private String password;
    private String retryPassword;
    private int currencyId;

}
