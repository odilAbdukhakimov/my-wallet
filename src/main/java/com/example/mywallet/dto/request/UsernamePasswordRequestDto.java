package com.example.mywallet.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UsernamePasswordRequestDto {
    @NotBlank(message = "Username cannot be null or whitespace")
    private String username;
    @NotBlank(message = "Password cannot be null or whitespace")
    private String password;
}
