package com.commerce.web.exceptions;

public class UserIsAlreadyVerifiedException extends RuntimeException {

    public UserIsAlreadyVerifiedException(String msg, Throwable t) {
        super(msg, t);
    }

    public UserIsAlreadyVerifiedException(String msg) {
        super(msg);
    }
}
