package com.commerce.web.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PasswordResetDTO {

    @NotBlank(message = "Provide reset token")
    private String token;

    @NotBlank(message = "Provide password")
    private String password;

}
