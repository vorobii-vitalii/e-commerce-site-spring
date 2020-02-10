package com.commerce.web.exceptions;

public class PasswordResetTokenIsNotValid extends Exception {

    public PasswordResetTokenIsNotValid(String msg,Throwable t) {
        super(msg,t);
    }

    public PasswordResetTokenIsNotValid(String msg) {
        super(msg);
    }

}
