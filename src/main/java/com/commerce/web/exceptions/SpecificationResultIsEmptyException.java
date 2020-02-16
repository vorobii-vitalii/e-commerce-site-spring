package com.commerce.web.exceptions;

public class SpecificationResultIsEmptyException extends Exception {

    public SpecificationResultIsEmptyException(String msg,Throwable t) {
        super(msg,t);
    }

    public SpecificationResultIsEmptyException(String msg) {
        super(msg);
    }

}
