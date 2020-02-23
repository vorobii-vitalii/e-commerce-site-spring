package com.commerce.web.rest;

import com.commerce.web.dto.ProfileDTO;
import com.commerce.web.dto.UserDTO;
import com.commerce.web.exceptions.UserWasNotFoundByEmailException;
import com.commerce.web.model.User;
import com.commerce.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/v1/profile")
public class ProfileRestController {

    private final UserService userService;

    @Autowired
    public ProfileRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "")
    public ResponseEntity<ProfileDTO> getInfo(Authentication authentication) throws UserWasNotFoundByEmailException {
        User ownerOfProfile = userService.findByEmail(authentication.getName());
        return new ResponseEntity<>(ProfileDTO.fromUser(ownerOfProfile), HttpStatus.OK);
    }

    @PostMapping(value = "")
    @ResponseStatus(HttpStatus.OK)
    public void editProfile(@RequestBody @Valid UserDTO userDTO, Authentication authentication) throws UserWasNotFoundByEmailException {
        User ownerOfProfile = userService.findByEmail(authentication.getName());
        userService.editProfile(userDTO, ownerOfProfile);
    }

}
