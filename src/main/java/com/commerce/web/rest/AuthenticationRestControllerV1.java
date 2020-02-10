package com.commerce.web.rest;

import com.commerce.web.constants.SiteConstants;
import com.commerce.web.dto.AuthenticationRequestDTO;
import com.commerce.web.dto.RegistrationRequestDTO;
import com.commerce.web.dto.UserDTO;
import com.commerce.web.dto.VerifyAccountDTO;
import com.commerce.web.exceptions.UserIsAlreadyVerifiedException;
import com.commerce.web.exceptions.UserWasNotFoundByEmailException;
import com.commerce.web.exceptions.VerificationTokenExpiredException;
import com.commerce.web.exceptions.VerificationTokenHasNotMatchedException;
import com.commerce.web.mail_templates.AccountValidationMailMessage;
import com.commerce.web.model.Status;
import com.commerce.web.model.User;
import com.commerce.web.security.jwt.JwtTokenProvider;
import com.commerce.web.service.MailService;
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

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final MailService mailService;

    private final UserService userService;

    @Autowired
    public AuthenticationRestControllerV1(
            AuthenticationManager authenticationManager,
            JwtTokenProvider jwtTokenProvider,
            MailService mailService,
            UserService userService
    ){
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.mailService = mailService;
        this.userService = userService;
    }

    @PostMapping(value = "register")
    public ResponseEntity register( @RequestBody RegistrationRequestDTO registrationRequestDTO ) {

        User providedUser = registrationRequestDTO.toUser ();

        User registeredUser = null;

        try {
            registeredUser = userService.register ( providedUser );
        }
        catch (Exception ignored) { }

        if ( registeredUser == null ) {
            throw new BadCredentialsException ( "Invalid credentials" );
        }

        String email = registeredUser.getEmail ();
        String token = registeredUser.getUserVerification ().getToken ();

        // Send email to person
        this.mailService.send ( new AccountValidationMailMessage(email,token, SiteConstants.BASE_URL ).generate () );

        return (ResponseEntity) ResponseEntity.ok ( );
    }

    @PostMapping(value = "login")
    public ResponseEntity login( @RequestBody AuthenticationRequestDTO requestDTO ) {

        try {

            String email = requestDTO.getEmail ();

            authenticationManager.authenticate (
                    new UsernamePasswordAuthenticationToken (
                            email,
                            requestDTO.getPassword ()
                    )
            );

            User user = userService.findByEmail ( email );

            if( user == null ) {
                throw new UsernameNotFoundException ( "User with email " + email + " not found");
            }
            else if( user.getStatus () != Status.ACTIVE ) {
                throw new BadCredentialsException ( "User with email " + email + " is not verified or deleted");
            }

            String token = jwtTokenProvider.createToken ( email, user.getRoles () );

            Map<Object,Object> response = new HashMap<> ();
            response.put ( "email" , email );
            response.put ( "token" , token );

            return ResponseEntity.ok ( response );
        }
        catch(AuthenticationException  e) {
            throw new BadCredentialsException ( "Invalid email or password" );
        }
    }

    @GetMapping(value = "verify/{token}")
    public ResponseEntity<UserDTO> verify( @PathVariable(name="token") String token) throws VerificationTokenExpiredException {

        try {
            User verifiedUser = userService.verifyByToken ( token );

            return ResponseEntity.ok ( UserDTO.fromUser ( verifiedUser) );
        }
        catch(VerificationTokenExpiredException | VerificationTokenHasNotMatchedException e) {
            throw new BadCredentialsException ( e.getMessage () );
        }

    }

    @PostMapping(value = "resend")
    public ResponseEntity resendToken( @RequestBody VerifyAccountDTO verifyAccountDTO ) throws UserIsAlreadyVerifiedException, UserWasNotFoundByEmailException {

        String email = verifyAccountDTO.getEmail ();

        try {
            String token = userService.regenerateToken ( email );

            // Resend token to person
            this.mailService.send ( new AccountValidationMailMessage(email,token, SiteConstants.BASE_URL ).generate () );

            return (ResponseEntity) ResponseEntity.ok ( );
        }
        catch(UserIsAlreadyVerifiedException | UserWasNotFoundByEmailException e) {
            throw new BadCredentialsException ( e.getMessage () );
        }

    }

}
