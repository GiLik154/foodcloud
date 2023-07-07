package com.example.foodcloud.exception;

public class UserNameDuplicateException extends RuntimeException {
    public UserNameDuplicateException(String msg) {
        super(msg);
    }
}
