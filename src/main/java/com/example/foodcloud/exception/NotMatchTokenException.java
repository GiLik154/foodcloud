package com.example.foodcloud.exception;

public class NotMatchTokenException extends RuntimeException {
    public NotMatchTokenException() {
        super("Not match token");
    }
}
