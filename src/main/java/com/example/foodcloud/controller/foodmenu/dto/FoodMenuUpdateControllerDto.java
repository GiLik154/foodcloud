package com.example.foodcloud.controller.foodmenu.dto;

import com.example.foodcloud.domain.foodmenu.service.update.dto.FoodMenuUpdateServiceDto;
import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class FoodMenuUpdateControllerDto {

    @NotBlank
    private final String name;
    @NotNull
    @Min(1000)
    @Max(3000000)
    private final int price;
    @NotBlank
    private final String foodType;
    @NotBlank
    private final String temperature;
    @NotBlank
    private final String meatType;
    @NotBlank
    private final String vegetables;

    public FoodMenuUpdateControllerDto(String name, int price, String foodType, String temperature, String meatType, String vegetables) {
        this.name = name;
        this.price = price;
        this.foodType = foodType;
        this.temperature = temperature;
        this.meatType = meatType;
        this.vegetables = vegetables;
    }

    public FoodMenuUpdateServiceDto convert(){
        return new FoodMenuUpdateServiceDto(this.name,
                this.price,
                this.foodType,
                this.temperature,
                this.meatType,
                this.vegetables);
    }
}
