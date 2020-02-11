package com.commerce.web.exceptions;

public class UsersResultIsEmptyException extends Exception {

    public UsersResultIsEmptyException(String msg,Throwable t) {
        super(msg,t);
    }

    public UsersResultIsEmptyException(String msg) {
        super(msg);
    }

}
