package com.commerce.web.rest.exception_handlers;

import com.commerce.web.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class UsersRestExceptionHandler {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UsersEmailIsTakenException.class)
    public Map<String,String> handleEmailIsTaken( UsersEmailIsTakenException ex) {
        Map<String,String> body = new HashMap<> ( );
        body.put ( "error", "Email has been already taken" );
        return body;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UsersResultIsEmptyException.class)
    public Map<String,String> handleUsersResultIsEmpty( UsersResultIsEmptyException ex) {
        Map<String,String> body = new HashMap<> ( );
        body.put ( "error", "Users were not found" );
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
    @ExceptionHandler(UserWasNotFoundException.class)
    public Map<String,String> handleUserWasNotFound(UserWasNotFoundException ex) {
        Map<String,String> body = new HashMap<> ( );
        body.put ( "error", "User wasn't found" );
        return body;
    }

}
