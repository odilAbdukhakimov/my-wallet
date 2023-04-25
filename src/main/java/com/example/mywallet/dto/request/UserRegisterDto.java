package com.example.mywallet.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserRegisterDto {
    private String name;
    @NotBlank(message = "Username cannot be null or whitespace")
    private String username;
    @NotBlank(message = "Password cannot be null or whitespace")
    private String password;
    private String retryPassword;
    private int currencyId;

}
