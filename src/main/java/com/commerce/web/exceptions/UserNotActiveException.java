package com.commerce.web.exceptions;

public class UserNotActiveException extends Exception {

    public UserNotActiveException(String msg,Throwable t) {
        super(msg,t);
    }

    public UserNotActiveException(String msg) {
        super(msg);
    }

}
