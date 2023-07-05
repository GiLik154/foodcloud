package com.example.foodcloud.exception;

public class NotFoundRestaurantException extends RuntimeException {
    public NotFoundRestaurantException(String msg) {
        super(msg);
    }
}
