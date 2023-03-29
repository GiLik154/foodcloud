package com.example.foodcloud.controller.core.foodmenu.dto;

import com.example.foodcloud.domain.foodmenu.service.update.dto.FoodMenuUpdateServiceDto;
import com.example.foodcloud.enums.foodmenu.FoodTypes;
import com.example.foodcloud.enums.foodmenu.MeatTypes;
import com.example.foodcloud.enums.foodmenu.Temperature;
import com.example.foodcloud.enums.foodmenu.Vegetables;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FoodMenuUpdateControllerDtoTest {
    @Test
    void Post_음식_메뉴_업데이트_Dto() {
        FoodMenuUpdateControllerDto foodMenuUpdateControllerDto = new FoodMenuUpdateControllerDto("testName", 123456789, Temperature.COLD, FoodTypes.ADE, MeatTypes.CHICKEN, Vegetables.FEW);

        assertEquals("testName", foodMenuUpdateControllerDto.getName());
        assertEquals(123456789, foodMenuUpdateControllerDto.getPrice());
        assertEquals(Temperature.COLD, foodMenuUpdateControllerDto.getTemperature());
        assertEquals(FoodTypes.ADE, foodMenuUpdateControllerDto.getFoodTypes());
        assertEquals(MeatTypes.CHICKEN, foodMenuUpdateControllerDto.getMeatType());
        assertEquals(Vegetables.FEW, foodMenuUpdateControllerDto.getVegetables());
    }

    @Test
    void Post_음식_메뉴_업데이트_Dto_Convert() {
        FoodMenuUpdateControllerDto foodMenuUpdateControllerDto = new FoodMenuUpdateControllerDto("testName", 123456789, Temperature.COLD, FoodTypes.ADE, MeatTypes.CHICKEN, Vegetables.FEW);
        FoodMenuUpdateServiceDto foodMenuUpdateServiceDto = foodMenuUpdateControllerDto.convert();

        assertEquals("testName", foodMenuUpdateServiceDto.getName());
        assertEquals(123456789, foodMenuUpdateServiceDto.getPrice());
        assertEquals(Temperature.COLD, foodMenuUpdateServiceDto.getTemperature());
        assertEquals(FoodTypes.ADE, foodMenuUpdateServiceDto.getFoodTypes());
        assertEquals(MeatTypes.CHICKEN, foodMenuUpdateServiceDto.getMeatType());
        assertEquals(Vegetables.FEW, foodMenuUpdateServiceDto.getVegetables());
    }
}