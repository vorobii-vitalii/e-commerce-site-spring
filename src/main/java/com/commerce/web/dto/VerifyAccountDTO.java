package com.commerce.web.dto;

import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class VerifyAccountDTO {

    @Email
    private String email;

}
