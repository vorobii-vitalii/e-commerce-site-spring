package com.commerce.web.exceptions;

public class UserWasNotFoundException extends RuntimeException {

    public UserWasNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public UserWasNotFoundException(String msg) {
        super(msg);
    }

}
