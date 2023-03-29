package com.example.foodcloud.domain.foodmenu.service.update.dto;

import com.example.foodcloud.enums.foodmenu.FoodTypes;
import com.example.foodcloud.enums.foodmenu.MeatTypes;
import com.example.foodcloud.enums.foodmenu.Temperature;
import com.example.foodcloud.enums.foodmenu.Vegetables;
import lombok.Getter;

@Getter
public class FoodMenuUpdateServiceDto {
    private final String name;
    private final int price;
    private final Temperature temperature;
    private final FoodTypes foodTypes;
    private final MeatTypes meatType;
    private final Vegetables vegetables;

    public FoodMenuUpdateServiceDto(String name, int price, Temperature temperature, FoodTypes foodTypes, MeatTypes meatType, Vegetables vegetables) {
        this.name = name;
        this.price = price;
        this.temperature = temperature;
        this.foodTypes = foodTypes;
        this.meatType = meatType;
        this.vegetables = vegetables;
    }
}
