package com.example.foodcloud.domain.foodmenu.service.update.dto;

import lombok.Getter;

@Getter
public class FoodMenuUpdateServiceDto {
    private final String name;
    private final int price;
    private final String foodType;
    private final String temperature;
    private final String meatType;
    private final String vegetables;

    public FoodMenuUpdateServiceDto(String name, int price, String foodType, String temperature, String meatType, String vegetables) {
        this.name = name;
        this.price = price;
        this.foodType = foodType;
        this.temperature = temperature;
        this.meatType = meatType;
        this.vegetables = vegetables;
    }
}
