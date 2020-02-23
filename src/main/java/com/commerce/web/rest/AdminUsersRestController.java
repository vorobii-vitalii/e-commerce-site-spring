package com.commerce.web.rest;

import com.commerce.web.dto.UserDTO;
import com.commerce.web.exceptions.*;
import com.commerce.web.model.User;
import com.commerce.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/v1/admin/")
public class AdminUsersRestController {

    private final UserService userService;

    @Autowired
    public AdminUsersRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "users")
    public ResponseEntity<List<UserDTO>> getAllUsers() throws UsersResultIsEmptyException {

        List<User> users = userService.getAll();

        return new ResponseEntity<>(
                users.stream().map(UserDTO::fromUser).collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @GetMapping(value = "users/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable(name = "id") Long id) throws UserWasNotFoundException, UserIsDeletedException {
        User user = userService.findById(id);
        return new ResponseEntity<>(UserDTO.fromUser(user), HttpStatus.OK);
    }


    @RequestMapping(value = "users/edit/{id}", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void changeUserById(@PathVariable(name = "id") Long id, @Valid @RequestBody UserDTO userDTO) throws UserWasNotFoundException, RolesAreInvalidException, AdminIsImmutableException {
        userService.changeUser(id, userDTO);
    }

    @RequestMapping(value = "users/{id}", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteUserById(@PathVariable(name = "id") Long id) throws UserWasNotFoundException, AdminIsImmutableException {
        userService.deleteUser(id);
    }


}
