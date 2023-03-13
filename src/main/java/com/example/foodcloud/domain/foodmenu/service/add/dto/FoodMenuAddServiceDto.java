package com.example.foodcloud.domain.foodmenu.service.add.dto;

import lombok.Getter;

@Getter
public class FoodMenuAddServiceDto {
    private final String foodMenu;
    private final int price;
    private final String foodType;
    private final String temperature;
    private final String meatType;
    private final String vegetables;

    public FoodMenuAddServiceDto(String foodMenu, int price, String foodType, String temperature, String meatType, String vegetables) {
        this.foodMenu = foodMenu;
        this.price = price;
        this.foodType = foodType;
        this.temperature = temperature;
        this.meatType = meatType;
        this.vegetables = vegetables;
    }
}
