package com.example.foodcloud.exception;

public class NotFoundRestaurantException extends RuntimeException {
    public NotFoundRestaurantException() {
        super("Not found BankAccount");
    }
}
