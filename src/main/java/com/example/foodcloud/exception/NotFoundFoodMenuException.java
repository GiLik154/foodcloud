package com.example.foodcloud.exception;

public class NotFoundFoodMenuException extends RuntimeException {
    public NotFoundFoodMenuException() {
        super("Not found FoodMenu");
    }
}
