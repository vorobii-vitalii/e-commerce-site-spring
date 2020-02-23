package com.commerce.web.exceptions;

public class UsersResultIsEmptyException extends RuntimeException {

    public UsersResultIsEmptyException(String msg, Throwable t) {
        super(msg, t);
    }

    public UsersResultIsEmptyException(String msg) {
        super(msg);
    }

}
