package com.commerce.web.exceptions;

public class ReviewOwnerWrongException extends RuntimeException {

    public ReviewOwnerWrongException(String msg, Throwable t) {
        super(msg, t);
    }

    public ReviewOwnerWrongException(String msg) {
        super(msg);
    }

}
