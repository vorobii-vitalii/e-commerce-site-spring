package com.commerce.web.exceptions;

public class VerificationTokenHasNotMatchedException extends Exception {

    public VerificationTokenHasNotMatchedException(String msg, Throwable t) {
        super(msg,t);
    }

    public VerificationTokenHasNotMatchedException(String msg) {
        super ( msg );
    }

}
