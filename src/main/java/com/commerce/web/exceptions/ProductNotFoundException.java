package com.commerce.web.exceptions;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public ProductNotFoundException(String msg) {
        super(msg);
    }

}
