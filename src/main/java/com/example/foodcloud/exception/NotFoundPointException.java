package com.example.foodcloud.exception;

public class NotFoundPointException extends RuntimeException {
    public NotFoundPointException() {
        super("Not found Restaurant");
    }
}
