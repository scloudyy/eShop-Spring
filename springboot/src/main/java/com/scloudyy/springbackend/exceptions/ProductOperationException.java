package com.scloudyy.springbackend.exceptions;

public class ProductOperationException extends RuntimeException {
    public ProductOperationException(String msg) {
        super(msg);
    }
}
