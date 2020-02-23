package com.commerce.web.exceptions;

public class PasswordResetTokenIsNotValid extends RuntimeException {

    public PasswordResetTokenIsNotValid(String msg, Throwable t) {
        super(msg, t);
    }

    public PasswordResetTokenIsNotValid(String msg) {
        super(msg);
    }

}
