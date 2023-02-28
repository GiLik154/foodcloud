package com.example.foodcloud.exception;

public class OutOfBoundsPointException extends RuntimeException {
    public OutOfBoundsPointException(String message) {
        super("Out of bounds for point " + message);
    }
}
