package com.example.foodcloud.exception;

public class NotFoundBankCodeException extends RuntimeException {
    public NotFoundBankCodeException() {
        super("Not found BankCode");
    }
}
