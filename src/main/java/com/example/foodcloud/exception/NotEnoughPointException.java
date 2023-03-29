package com.example.foodcloud.exception;

public class NotEnoughPointException extends RuntimeException {
    public NotEnoughPointException(String message) {
        super("Out of bounds for point " + message);
    }
}
