package com.commerce.web.rest;

import com.commerce.web.dto.UserDTO;
import com.commerce.web.model.User;
import com.commerce.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/admin/")
public class AdminRestController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "users")
    public ResponseEntity<List<UserDTO>> getAllUsers( ){

        List<User> users = userService.getAll ();

        if(users == null) {
            return new ResponseEntity<> ( HttpStatus.NO_CONTENT );
        }

        return new ResponseEntity (
                users
                        .stream ()
                        .map ( user -> UserDTO.fromUser ( user ) )
                        .collect ( Collectors.toList ( ) ),
                HttpStatus.OK);
    }

    @GetMapping(value="users/{id}")
    public ResponseEntity<UserDTO> getUserById( @PathVariable(name="id") Long id) {

        User user = userService.findById ( id );

        if(user == null) {
            return new ResponseEntity<> ( HttpStatus.NO_CONTENT );
        }

        UserDTO userDTO = UserDTO.fromUser ( user );

        return new ResponseEntity<UserDTO> ( userDTO, HttpStatus.OK );
    }



}
