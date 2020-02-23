package com.commerce.web.exceptions;

public class ProductsResultIsEmptyException extends RuntimeException {

    public ProductsResultIsEmptyException(String msg, Throwable t) {
        super(msg, t);
    }

    public ProductsResultIsEmptyException(String msg) {
        super(msg);
    }

}
