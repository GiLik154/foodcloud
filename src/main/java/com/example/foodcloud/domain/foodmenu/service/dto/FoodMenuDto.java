package com.example.foodcloud.domain.foodmenu.service.dto;

import lombok.Getter;

@Getter
public class FoodMenuDto {
    private String foodMenu;
    private int price;
    private String foodType;
    private String temperature;
    private String meatType;
    private String vegetables;

    public FoodMenuDto(String foodMenu, int price, String foodType, String temperature, String meatType, String vegetables) {
        this.foodMenu = foodMenu;
        this.price = price;
        this.foodType = foodType;
        this.temperature = temperature;
        this.meatType = meatType;
        this.vegetables = vegetables;
    }
}
