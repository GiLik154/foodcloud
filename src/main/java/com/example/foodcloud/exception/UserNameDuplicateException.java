package com.example.foodcloud.exception;

public class UserNameDuplicateException extends RuntimeException {
    public UserNameDuplicateException() {
        super("Duplicate User Name");
    }
}
