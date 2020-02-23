package com.commerce.web.exceptions;

public class SpecificationNotFoundException extends RuntimeException {

    public SpecificationNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public SpecificationNotFoundException(String msg) {
        super(msg);

    }

}
