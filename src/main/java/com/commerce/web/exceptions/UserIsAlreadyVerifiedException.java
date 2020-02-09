package com.commerce.web.exceptions;

public class UserIsAlreadyVerifiedException extends Exception {

    public UserIsAlreadyVerifiedException(String msg,Throwable t) {
        super(msg,t);
    }

    public UserIsAlreadyVerifiedException(String msg) {
        super(msg);
    }
}
