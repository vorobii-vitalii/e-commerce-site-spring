package com.commerce.web.dto;

import com.commerce.web.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.Email;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO {

    private Long id;
    private String firstName;
    private String lastName;

    @Email
    private String email;

    public User toUser() {
        User user = new User ();
        user.setId ( id );
        user.setFirstName ( firstName );
        user.setLastName ( lastName );
        user.setEmail ( email );

        return user;
    }

    public static UserDTO fromUser(User user) {
        UserDTO userDTO = new UserDTO ();
        userDTO.setId ( user.getId ( ) );
        userDTO.setEmail ( user.getEmail () );
        userDTO.setFirstName ( user.getFirstName () );
        userDTO.setLastName ( user.getLastName () );
        return userDTO;
    }
}
