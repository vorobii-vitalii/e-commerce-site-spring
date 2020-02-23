package com.commerce.web.exceptions;

public class NotImplementedException extends Exception {

    public NotImplementedException(String msg, Throwable t) {
        super(msg, t);
    }

    public NotImplementedException(String msg) {
        super(msg);
    }

}
