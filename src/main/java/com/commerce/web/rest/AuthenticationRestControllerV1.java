package com.commerce.web.rest;

import com.commerce.web.constants.SiteConstants;
import com.commerce.web.dto.AuthenticationRequestDTO;
import com.commerce.web.dto.RegistrationRequestDTO;
import com.commerce.web.dto.UserDTO;
import com.commerce.web.dto.VerifyAccountDTO;
import com.commerce.web.exceptions.*;
import com.commerce.web.mail_templates.AccountValidationMailMessage;
import com.commerce.web.model.Status;
import com.commerce.web.model.User;
import com.commerce.web.security.jwt.JwtTokenProvider;
import com.commerce.web.service.MailService;
import com.commerce.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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

    // Endpoints

    @PostMapping(value = "register")
    @ResponseStatus(value = HttpStatus.OK)
    public void register( @Valid @RequestBody RegistrationRequestDTO registrationRequestDTO ) {

        User registeredUser = userService.register ( registrationRequestDTO.toUser () );

        String email = registeredUser.getEmail ();
        String token = registeredUser.getUserVerification ().getToken ();

        // Send email to person
        this.mailService.send ( new AccountValidationMailMessage(email,token, SiteConstants.BASE_URL ).generate () );
    }


    @PostMapping(value = "login")
    public ResponseEntity login( @Valid @RequestBody AuthenticationRequestDTO requestDTO ) throws UserWasNotFoundException, UserNotActiveException {

        String email = requestDTO.getEmail ();

        authenticationManager.authenticate ( new UsernamePasswordAuthenticationToken ( email, requestDTO.getPassword () ) );

        User user = userService.findByEmail ( email );

        if( user == null )  throw new UserWasNotFoundException ( email );
        if( user.getStatus () != Status.ACTIVE )  throw new UserNotActiveException ( email );

        String token = jwtTokenProvider.createToken ( email, user.getRoles () );

        Map<Object,Object> response = new HashMap<> ();
        response.put ( "email" , email );
        response.put ( "token" , token );

        return ResponseEntity.ok ( response );
    }

    @GetMapping(value = "verify/{token}")
    public ResponseEntity<UserDTO> verify( @PathVariable(name="token") String token) throws VerificationTokenExpiredException,VerificationTokenHasNotMatchedException {

        User verifiedUser = userService.verifyByToken ( token );

        return ResponseEntity.ok ( UserDTO.fromUser ( verifiedUser) );
    }

    @PostMapping(value = "resend")
    @ResponseStatus(value = HttpStatus.OK)
    public void resendToken( @Valid @RequestBody VerifyAccountDTO verifyAccountDTO ) throws UserIsAlreadyVerifiedException, UserWasNotFoundByEmailException {

        String email = verifyAccountDTO.getEmail ();
        String token = userService.regenerateToken ( email );

        // Resend token to person
        this.mailService.send ( new AccountValidationMailMessage(email,token, SiteConstants.BASE_URL ).generate () );
    }


    // Exception handlers

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UserNotActiveException.class)
    public Map<String,String> handleUserNotActive(UserNotActiveException ex) {
        Map<String,String> body = new HashMap<> ( );
        body.put ( "error", "User with email " + ex.getMessage () + " is not active" );
        return body;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserWasNotFoundException.class)
    public Map<String,String> handleUserWasNotFound(UserWasNotFoundException ex) {
        Map<String,String> body = new HashMap<> ( );
        body.put ( "error", "User with email " + ex.getMessage () + " wasn't found" );
        return body;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AuthenticationException.class)
    public Map<String,String> handleAuthenticationError(AuthenticationException ex) {
        Map<String,String> body = new HashMap<> ( );
        body.put ( "error", "Failed to authenticate" );
        return body;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(VerificationTokenHasNotMatchedException.class)
    public Map<String,String> handleVerificationTokenHasNotMatched(VerificationTokenHasNotMatchedException ex) {
        Map<String,String> body = new HashMap<> ( );
        body.put ( "error", "Verification token has not matched" );
        return body;
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(VerificationTokenExpiredException.class)
    public Map<String,String> handleTokenWasExpired(VerificationTokenExpiredException ex) {
        Map<String,String> body = new HashMap<> ( );
        body.put ( "error", "Token has been expired" );
        return body;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserIsAlreadyVerifiedException.class)
    public Map<String,String> handleUserWasNotFoundByEmail(UserWasNotFoundByEmailException ex) {
        Map<String,String> body = new HashMap<> ( );
        body.put ( "error", "User wasn't found by email" );
        return body;
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UserIsAlreadyVerifiedException.class)
    public Map<String,String> handleUserIsAlreadyVerified(UserIsAlreadyVerifiedException ex) {
        Map<String,String> body = new HashMap<> ( );
        body.put ( "error", "User is already verified" );
        return body;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleMethodArgumentNotValid( MethodArgumentNotValidException ex) {
        return ex.getBindingResult ( ).getFieldErrors ( ).stream ( ).collect ( Collectors.toMap ( FieldError::getField ,
                DefaultMessageSourceResolvable::getDefaultMessage ,
                ( a , b ) -> b ) );
    }

}
