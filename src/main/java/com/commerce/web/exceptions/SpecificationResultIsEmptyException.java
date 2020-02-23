package com.commerce.web.exceptions;

public class SpecificationResultIsEmptyException extends RuntimeException {

    public SpecificationResultIsEmptyException(String msg, Throwable t) {
        super(msg, t);
    }

    public SpecificationResultIsEmptyException(String msg) {
        super(msg);
    }

}
