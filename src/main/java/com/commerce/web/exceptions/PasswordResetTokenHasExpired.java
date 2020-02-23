package com.commerce.web.exceptions;

public class PasswordResetTokenHasExpired extends RuntimeException {

    public PasswordResetTokenHasExpired(String msg, Throwable t) {
        super(msg, t);
    }

    public PasswordResetTokenHasExpired(String msg) {
        super(msg);
    }

}
