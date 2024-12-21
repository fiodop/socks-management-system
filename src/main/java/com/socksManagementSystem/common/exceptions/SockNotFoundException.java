package com.socksManagementSystem.common.exceptions;

public class SockNotFoundException extends RuntimeException {
    public SockNotFoundException(String message) {
        super(message);
    }
}
