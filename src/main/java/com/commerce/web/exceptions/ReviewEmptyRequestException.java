package com.commerce.web.exceptions;

public class ReviewEmptyRequestException extends RuntimeException {

    public ReviewEmptyRequestException(String msg, Throwable t) {
        super(msg, t);
    }

    public ReviewEmptyRequestException(String msg) {
        super(msg);
    }

}
