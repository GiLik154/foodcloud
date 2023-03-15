package com.example.foodcloud.exception;

public class NotFoundErrorCodeException extends RuntimeException {
    public NotFoundErrorCodeException() {
        super("Not found BankAccount");
    }
}
