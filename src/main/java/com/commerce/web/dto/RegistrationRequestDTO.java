package com.commerce.web.dto;

import com.commerce.web.model.User;
import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class RegistrationRequestDTO {

    @Email
    private String email;

    private String password;

    private String firstName;

    private String lastName;


    public User toUser() {

        User user = new User ();

        user.setEmail ( email );
        user.setFirstName ( firstName );
        user.setLastName ( lastName );
        user.setPassword ( password );

        return user;
    }

}
