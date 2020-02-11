package com.commerce.web.exceptions;

public class UserIsDeletedException extends Exception {

    public UserIsDeletedException(String msg,Throwable t) {
        super(msg,t);
    }

    public UserIsDeletedException(String msg) {
        super(msg);
    }

}
