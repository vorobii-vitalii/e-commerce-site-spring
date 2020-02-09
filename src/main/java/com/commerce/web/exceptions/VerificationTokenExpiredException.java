package com.commerce.web.exceptions;

public class VerificationTokenExpiredException extends Exception {

    public VerificationTokenExpiredException(String msg,Throwable t) {
        super(msg,t);
    }

    public VerificationTokenExpiredException(String msg) {
        super(msg);
    }
}
