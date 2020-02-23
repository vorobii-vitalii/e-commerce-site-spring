package com.commerce.web.dto;

import com.commerce.web.model.Role;
import com.commerce.web.model.User;
import lombok.Data;

import java.util.List;

@Data
public class ProfileDTO {

    private String firstName;

    private String lastName;

    private String email;

    private List<Role> roles;

    public static ProfileDTO fromUser(User user) {
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setFirstName(user.getFirstName());
        profileDTO.setLastName(user.getLastName());
        profileDTO.setEmail(user.getEmail());
        profileDTO.setRoles(user.getRoles());
        return profileDTO;
    }

}
