package com.example.foodcloud.controller.core.foodmenu.dto;

import com.example.foodcloud.domain.foodmenu.service.commend.FoodMenuUpdaterCommend;
import com.example.foodcloud.enums.foodmenu.FoodTypes;
import com.example.foodcloud.enums.foodmenu.MeatTypes;
import com.example.foodcloud.enums.foodmenu.Temperature;
import com.example.foodcloud.enums.foodmenu.Vegetables;
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
    @NotNull
    private final Temperature temperature;
    @NotNull
    private final FoodTypes foodTypes;
    @NotNull
    private final MeatTypes meatType;
    @NotNull
    private final Vegetables vegetables;

    public FoodMenuUpdateControllerDto(String name, int price, Temperature temperature, FoodTypes foodTypes, MeatTypes meatType, Vegetables vegetables) {
        this.name = name;
        this.price = price;
        this.temperature = temperature;
        this.foodTypes = foodTypes;
        this.meatType = meatType;
        this.vegetables = vegetables;
    }

    public FoodMenuUpdaterCommend convert(){
        return new FoodMenuUpdaterCommend(this.name,
                this.price,
                this.temperature,
                this.foodTypes,
                this.meatType,
                this.vegetables);
    }
}
