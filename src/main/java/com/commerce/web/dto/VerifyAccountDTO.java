package com.commerce.web.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class VerifyAccountDTO {

    @Email
    @NotEmpty
    private String email;

}
