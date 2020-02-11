package com.commerce.web.exceptions;

import java.util.function.Supplier;

public class ProductNotFoundException extends Exception {

    public ProductNotFoundException(String msg,Throwable t) {
        super(msg,t);
    }

    public ProductNotFoundException(String msg) {
        super(msg);
    }

}
