package com.commerce.web.rest;

import com.commerce.web.dto.AuthenticationRequestDTO;
import com.commerce.web.dto.RegistrationRequestDTO;
import com.commerce.web.model.User;
import com.commerce.web.security.jwt.JwtAuthenthicationException;
import com.commerce.web.security.jwt.JwtTokenProvider;
import com.commerce.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationRestControllerV1 {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserService userService;

    @PostMapping(value = "register")
    public ResponseEntity register( @RequestBody RegistrationRequestDTO registrationRequestDTO ) {

        User providedUser = registrationRequestDTO.toUser ();

        User registeredUser = null;

        try {
            registeredUser = userService.register ( providedUser );
        }
        catch (Exception e) { }

        if (registeredUser == null) {
            throw new BadCredentialsException ( "Invalid credentials" );
        }

        String username = registeredUser.getUsername ();

        String token = jwtTokenProvider.createToken (
                username,
                registeredUser.getRoles ()
        );

        Map<Object,Object> response = new HashMap<> ();
        response.put ( "username" , username );
        response.put ( "token" , token );

        return ResponseEntity.ok ( response );
    }

    @PostMapping(value = "login")
    public ResponseEntity login( @RequestBody AuthenticationRequestDTO requestDTO ) {
        try {
            System.out.println ( "REQUEST : "+ requestDTO.getUsername () + " : "+ requestDTO.getPassword () );
            String username = requestDTO.getUsername ();
            authenticationManager.authenticate (
                    new UsernamePasswordAuthenticationToken (
                            username,
                            requestDTO.getPassword ()
                    )
            );
            User user = userService.findByUsername ( username );

            if(user == null) {
                throw new UsernameNotFoundException ( "User with username " + username + " not found");
            }


            String token = jwtTokenProvider.createToken ( username, user.getRoles () );

            Map<Object,Object> response = new HashMap<> ();
            response.put ( "username" , username );
            response.put ( "token" , token );

            return ResponseEntity.ok ( response );
        }
        catch(AuthenticationException  e) {
            throw new BadCredentialsException ( "Invalid username or password" );
        }
        catch(Exception e) {
            System.out.println ( "Something wrong happaned with LOGIN" );
            return null;
        }
    }
}
