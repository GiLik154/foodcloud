package com.example.foodcloud.exception;

public class PasswordEncodingFailedException extends RuntimeException {
    public PasswordEncodingFailedException() {
        super("Password Encoding Failed");
    }
}
