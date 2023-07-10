package com.example.foodcloud.exception;

public class NotFoundGroupByListException extends RuntimeException {
    public NotFoundGroupByListException(String msg) {
        super(msg);
    }
}
