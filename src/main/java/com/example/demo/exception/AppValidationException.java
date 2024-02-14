package com.example.demo.exception;

public class AppValidationException extends RuntimeException {

    public AppValidationException(String message) {
        super(message);
    }

    public AppValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
