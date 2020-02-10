package com.commerce.web.exceptions;

public class PasswordResetTokenHasExpired extends Exception {

    public PasswordResetTokenHasExpired(String msg,Throwable t) {
        super(msg,t);
    }

    public PasswordResetTokenHasExpired(String msg) {
        super(msg);
    }

}
