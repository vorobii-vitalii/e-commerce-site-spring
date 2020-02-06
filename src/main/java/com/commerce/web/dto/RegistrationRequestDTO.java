package com.commerce.web.dto;

import com.commerce.web.model.User;
import lombok.Data;

@Data
public class RegistrationRequestDTO {

    private String username;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    public User toUser() {

        User user = new User ();

        user.setEmail ( email );
        user.setFirstName ( firstName );
        user.setLastName ( lastName );
        user.setUsername ( username );
        user.setPassword ( password );

        return user;
    }

}
