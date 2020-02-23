package com.commerce.web.exceptions;

public class SpecificationNameIsTakenException extends RuntimeException {

    public SpecificationNameIsTakenException(String msg, Throwable t) {
        super(msg, t);
    }

    public SpecificationNameIsTakenException(String msg) {
        super(msg);
    }

}
