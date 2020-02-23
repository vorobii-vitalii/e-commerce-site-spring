package com.commerce.web.exceptions;

public class SpecificationNotFoundByNameException extends RuntimeException {

    public SpecificationNotFoundByNameException(String msg, Throwable t) {
        super(msg, t);
    }

    public SpecificationNotFoundByNameException(String msg) {
        super(msg);
    }

}
