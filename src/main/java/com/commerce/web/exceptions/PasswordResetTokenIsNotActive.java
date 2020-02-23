package com.commerce.web.exceptions;

public class PasswordResetTokenIsNotActive extends RuntimeException {

    public PasswordResetTokenIsNotActive(String msg, Throwable t) {
        super(msg, t);
    }

    public PasswordResetTokenIsNotActive(String msg) {
        super(msg);
    }

}
