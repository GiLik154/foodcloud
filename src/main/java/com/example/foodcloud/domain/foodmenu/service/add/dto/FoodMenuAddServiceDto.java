package com.example.foodcloud.domain.foodmenu.service.add.dto;

import lombok.Getter;

@Getter
public class FoodMenuAddServiceDto {
    private final String name;
    private final int price;
    private final String foodType;
    private final String temperature;
    private final String meatType;
    private final String vegetables;

    public FoodMenuAddServiceDto(String name, int price, String foodType, String temperature, String meatType, String vegetables) {
        this.name = name;
        this.price = price;
        this.foodType = foodType;
        this.temperature = temperature;
        this.meatType = meatType;
        this.vegetables = vegetables;
    }
}
