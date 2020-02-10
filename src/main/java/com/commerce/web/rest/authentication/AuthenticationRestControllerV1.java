package com.commerce.web.rest.authentication;

import com.commerce.web.constants.SiteConstants;
import com.commerce.web.dto.*;
import com.commerce.web.exceptions.*;
import com.commerce.web.mail_templates.AccountValidationMailMessage;
import com.commerce.web.mail_templates.ForgotPasswordMailMessage;
import com.commerce.web.model.Status;
import com.commerce.web.model.User;
import com.commerce.web.security.jwt.JwtTokenProvider;
import com.commerce.web.service.MailService;
import com.commerce.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
        String token = userService.regenerateVerificationToken ( email );

        // Resend token to person
        this.mailService.send ( new AccountValidationMailMessage(email,token, SiteConstants.BASE_URL ).generate () );
    }


    @PostMapping(value = "forgotPassword")
    @ResponseStatus(value = HttpStatus.OK)
    public void forgotPassword(@Valid @RequestBody VerifyAccountDTO verifyAccountDTO) throws UserWasNotFoundByEmailException, UserNotActiveException {

        String email = verifyAccountDTO.getEmail ();

        String token = userService.resetPasswordByEmail ( email );

        this.mailService.send ( new ForgotPasswordMailMessage ( email,token,SiteConstants.BASE_URL ).generate () );
    }


    @PostMapping(value = "resetPassword")
    @ResponseStatus(value = HttpStatus.OK)
    public void resetPassword( @Valid @RequestBody PasswordResetDTO passwordResetDTO ) throws PasswordResetTokenIsNotActive, PasswordResetTokenIsNotValid, VerificationTokenExpiredException, PasswordResetTokenHasExpired {

        String token = passwordResetDTO.getToken ();

        String password = passwordResetDTO.getPassword ();

        this.userService.changePasswordByToken ( token, password );

    }

}
