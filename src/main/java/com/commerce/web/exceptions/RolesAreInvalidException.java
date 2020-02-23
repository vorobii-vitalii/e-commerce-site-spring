package com.commerce.web.exceptions;

public class RolesAreInvalidException extends RuntimeException {

    public RolesAreInvalidException(String msg, Throwable t) {
        super(msg, t);
    }

    public RolesAreInvalidException(String msg) {
        super(msg);
    }

}
