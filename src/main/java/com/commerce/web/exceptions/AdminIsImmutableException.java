package com.commerce.web.exceptions;

public class AdminIsImmutableException extends RuntimeException {

    public AdminIsImmutableException(String msg, Throwable t) {
        super(msg, t);
    }

    public AdminIsImmutableException(String msg) {
        super(msg);
    }

}
