package com.example.foodcloud.exception;

public class NotFoundFoodMenuException extends RuntimeException {
    public NotFoundFoodMenuException(String msg) {
        super(msg);
    }
}
