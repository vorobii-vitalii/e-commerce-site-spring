package com.commerce.web.rest.authentication;

import com.commerce.web.exceptions.*;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class AuthenticationRestExceptionHandler {


    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(PasswordResetTokenHasExpired.class)
    public Map<String,String> handlePasswordResetTokenHasExpired( PasswordResetTokenHasExpired ex) {
        Map<String,String> body = new HashMap<> ( );
        body.put ( "error", "Password reset token has expired" );
        return body;
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(AdminIsImmutableException.class)
    public Map<String,String> handlePAdminIsImmutable( AdminIsImmutableException ex) {
        Map<String,String> body = new HashMap<> ( );
        body.put ( "error", "Admin cannot be changed" );
        return body;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RolesAreInvalidException.class)
    public Map<String,String> handleRolesNotValid(RolesAreInvalidException ex) {
        Map<String,String> body = new HashMap<> ( );
        body.put ( "error", "Provided roles are invalid" );
        return body;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PasswordResetTokenIsNotValid.class)
    public Map<String,String> handlePasswordResetTokenIsNotValid(PasswordResetTokenIsNotValid ex) {
        Map<String,String> body = new HashMap<> ( );
        body.put ( "error", "Password reset token is not valid" );
        return body;
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(PasswordResetTokenIsNotActive.class)
    public Map<String,String> handlePasswordResetTokenIsNotActive( PasswordResetTokenIsNotActive ex) {
        Map<String,String> body = new HashMap<> ( );
        body.put ( "error", "Password reset token is not active" );
        return body;
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UserNotActiveException.class)
    public Map<String,String> handleUserNotActive( UserNotActiveException ex) {
        Map<String,String> body = new HashMap<> ( );
        body.put ( "error", "User with email " + ex.getMessage () + " is not active" );
        return body;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserWasNotFoundException.class)
    public Map<String,String> handleUserWasNotFound(UserWasNotFoundException ex) {
        Map<String,String> body = new HashMap<> ( );
        body.put ( "error", "User wasn't found" );
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

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UsersResultIsEmptyException.class)
    public Map<String,String> handleUsersResultIsEmpty(UsersResultIsEmptyException ex) {
        Map<String,String> body = new HashMap<> ( );
        body.put ( "error", "Users were not found" );
        return body;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserWasNotFoundByEmailException.class)
    public Map<String,String> handleUserWasNotFoundByEmail(UserWasNotFoundByEmailException ex) {
        Map<String,String> body = new HashMap<> ( );
        body.put ( "error", "User with email " + ex.getMessage () + " wasn't found" );
        return body;
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UserIsAlreadyVerifiedException.class)
    public Map<String,String> handleUserIsAlreadyVerified(UserIsAlreadyVerifiedException ex) {
        Map<String,String> body = new HashMap<> ( );
        body.put ( "error", "User is already verified" );
        return body;
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UserIsDeletedException.class)
    public Map<String,String> handleUserIsDeleted(UserIsDeletedException ex) {
        Map<String,String> body = new HashMap<> ( );
        body.put ( "error", "User is deleted" );
        return body;
    }


    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleMethodArgumentNotValid( MethodArgumentNotValidException ex) {
        return ex.getBindingResult ( ).getFieldErrors ( ).stream ( ).collect ( Collectors.toMap ( FieldError::getField ,
                DefaultMessageSourceResolvable::getDefaultMessage ,
                ( a , b ) -> b ) );
    }
}
