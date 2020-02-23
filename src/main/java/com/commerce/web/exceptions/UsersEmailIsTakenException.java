package com.commerce.web.exceptions;

public class UsersEmailIsTakenException extends RuntimeException {

    public UsersEmailIsTakenException(String msg, Throwable t) {
        super(msg, t);
    }

    public UsersEmailIsTakenException(String msg) {
        super(msg);
    }

}
