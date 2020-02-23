package com.commerce.web.rest.exception_handlers;

import com.commerce.web.exceptions.ProductNotFoundException;
import com.commerce.web.exceptions.ProductsResultIsEmptyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ProductsRestExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ProductsResultIsEmptyException.class)
    public Map<String, String> handleProductsResultIsEmpty(ProductsResultIsEmptyException ex) {
        Map<String, String> body = new HashMap<>();
        body.put("error", "Products were not found");
        return body;
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ProductNotFoundException.class)
    public Map<String, String> handleProductNotFoundException(ProductNotFoundException ex) {
        Map<String, String> body = new HashMap<>();
        body.put("error", "Product wasn't found");
        return body;
    }


}
