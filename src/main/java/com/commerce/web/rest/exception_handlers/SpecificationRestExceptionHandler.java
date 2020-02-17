package com.commerce.web.rest.exception_handlers;

import com.commerce.web.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class SpecificationRestExceptionHandler {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(SpecificationNameIsTakenException.class)
    public Map<String,String> handleSpecificationNameIsTaken( SpecificationNameIsTakenException ex) {
        Map<String,String> body = new HashMap<> ( );
        body.put ( "error", "Specification name is taken" );
        return body;
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(SpecificationNotFoundByNameException.class)
    public Map<String,String> handleSpecificationNotFoundByName( SpecificationNotFoundByNameException ex) {
        Map<String,String> body = new HashMap<> ( );
        body.put ( "error", "Specification was not found" );
        return body;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(SpecificationNotFoundException.class)
    public Map<String,String> handleSpecificationNotFound( SpecificationNotFoundException ex) {
        Map<String,String> body = new HashMap<> ( );
        body.put ( "error", "Specification was not found" );
        return body;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(SpecificationResultIsEmptyException.class)
    public Map<String,String> handleSpecificationResultIsEmpty( SpecificationResultIsEmptyException ex) {
        Map<String,String> body = new HashMap<> ( );
        body.put ( "error", "Specification result is empty" );
        return body;
    }

}
