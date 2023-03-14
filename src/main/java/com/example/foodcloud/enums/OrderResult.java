package com.example.foodcloud.enums;

public enum OrderResult {
    PAYMENT_WAITING("Payment waiting"),
    RECEIVED("Received"),
    COOKING("Cooking"),
    PREPARED("Prepared"),
    DELIVERED("Delivered"),
    COMPLETED("Completed"),
    CANCELED("Canceled");

    private final String result;

    OrderResult(String name) {
        this.result = name;
    }

    public String getResult() {
        return result;
    }
}