package com.commerce.web.dto;

import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class AuthenticationRequestDTO {

    @Email
    private String email;

    private String password;

}
