package com.example.foodcloud.controller.core.foodmenu.update.dto;

import com.example.foodcloud.controller.core.foodmenu.dto.FoodMenuUpdateControllerDto;
import com.example.foodcloud.domain.foodmenu.service.commend.FoodMenuUpdaterCommend;
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
        FoodMenuUpdaterCommend foodMenuUpdaterCommend = foodMenuUpdateControllerDto.convert();

        assertEquals("testName", foodMenuUpdaterCommend.getName());
        assertEquals(123456789, foodMenuUpdaterCommend.getPrice());
        assertEquals(Temperature.COLD, foodMenuUpdaterCommend.getTemperature());
        assertEquals(FoodTypes.ADE, foodMenuUpdaterCommend.getFoodTypes());
        assertEquals(MeatTypes.CHICKEN, foodMenuUpdaterCommend.getMeatType());
        assertEquals(Vegetables.FEW, foodMenuUpdaterCommend.getVegetables());
    }
}