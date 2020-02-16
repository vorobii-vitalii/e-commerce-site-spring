package com.commerce.web.exceptions;

public class SpecificationNotFoundByNameException extends Exception {

    public SpecificationNotFoundByNameException(String msg,Throwable t) {
        super(msg,t);
    }

    public SpecificationNotFoundByNameException(String msg) {
        super(msg);
    }

}
