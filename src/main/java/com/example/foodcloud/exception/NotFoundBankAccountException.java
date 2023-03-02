package com.example.foodcloud.exception;

public class NotFoundBankAccountException extends RuntimeException {
    public NotFoundBankAccountException() {
        super("Not found restaurant");
    }
}
