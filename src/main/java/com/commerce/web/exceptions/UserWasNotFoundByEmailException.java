package com.commerce.web.exceptions;

public class UserWasNotFoundByEmailException extends Exception {

    public UserWasNotFoundByEmailException(String msg,Throwable t) {
        super(msg,t);
    }

    public UserWasNotFoundByEmailException(String msg) {
        super(msg);
    }
}
