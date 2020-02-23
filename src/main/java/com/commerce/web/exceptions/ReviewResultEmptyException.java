package com.commerce.web.exceptions;

public class ReviewResultEmptyException extends RuntimeException {

    public ReviewResultEmptyException(String msg, Throwable t) {
        super(msg, t);
    }

    public ReviewResultEmptyException(String msg) {
        super(msg);
    }

}
