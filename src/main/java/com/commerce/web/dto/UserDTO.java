package com.commerce.web.dto;

import com.commerce.web.model.Status;
import com.commerce.web.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private Status status;

    public User toUser() {
        User user = new User ();
        user.setId ( id );
        user.setFirstName ( firstName );
        user.setLastName ( lastName );
        user.setEmail ( email );
        user.setStatus ( status );

        return user;
    }

    public static UserDTO fromUser(User user) {
        UserDTO userDTO = new UserDTO ();
        userDTO.setId ( user.getId ( ) );
        userDTO.setEmail ( user.getEmail () );
        userDTO.setFirstName ( user.getFirstName () );
        userDTO.setLastName ( user.getLastName () );
        userDTO.setStatus ( user.getStatus () );
        return userDTO;
    }
}
