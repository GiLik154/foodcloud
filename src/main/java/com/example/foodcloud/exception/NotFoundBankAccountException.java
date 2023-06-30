package com.example.foodcloud.exception;

public class NotFoundBankAccountException extends RuntimeException {
    public NotFoundBankAccountException(String msg) {
        super(msg);
    }
}
