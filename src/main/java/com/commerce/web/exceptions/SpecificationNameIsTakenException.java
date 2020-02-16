package com.commerce.web.exceptions;

public class SpecificationNameIsTakenException extends Exception {

    public SpecificationNameIsTakenException(String msg,Throwable t) {
        super(msg,t);
    }

    public SpecificationNameIsTakenException(String msg) {
        super(msg);
    }

}
