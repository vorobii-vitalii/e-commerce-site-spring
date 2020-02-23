package com.commerce.web.exceptions;

public class VerificationTokenExpiredException extends RuntimeException {

    public VerificationTokenExpiredException(String msg, Throwable t) {
        super(msg, t);
    }

    public VerificationTokenExpiredException(String msg) {
        super(msg);
    }
}
