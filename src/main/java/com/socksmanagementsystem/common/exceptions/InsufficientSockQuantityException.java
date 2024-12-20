package com.socksmanagementsystem.common.exceptions;

public class InsufficientSockQuantityException extends RuntimeException {
    public InsufficientSockQuantityException(String message) {
        super(message);
    }
}
