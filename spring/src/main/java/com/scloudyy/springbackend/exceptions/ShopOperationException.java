package com.scloudyy.springbackend.exceptions;

public class ShopOperationException extends RuntimeException {
    public ShopOperationException(String msg) {
        super(msg);
    }
}
