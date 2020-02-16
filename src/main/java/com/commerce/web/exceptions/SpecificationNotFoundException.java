package com.commerce.web.exceptions;

public class SpecificationNotFoundException extends Exception {

    public SpecificationNotFoundException(String msg,Throwable t) {
        super(msg,t);
    }

    public SpecificationNotFoundException(String msg) {
        super(msg);

    }

}
