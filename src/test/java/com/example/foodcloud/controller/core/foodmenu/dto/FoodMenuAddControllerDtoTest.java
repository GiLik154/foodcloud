package com.example.foodcloud.controller.core.foodmenu.dto;

import com.example.foodcloud.domain.foodmenu.service.add.dto.FoodMenuAddServiceDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FoodMenuAddControllerDtoTest {
    @Test
    void Post_계좌_업데이트_Dto() {
        FoodMenuAddControllerDto foodMenuAddControllerDto = new FoodMenuAddControllerDto("testName", 123456789, "testType", "testTemperature", "testMeat", "testVegetables");

        assertEquals("testName", foodMenuAddControllerDto.getName());
        assertEquals(123456789, foodMenuAddControllerDto.getPrice());
        assertEquals("testType", foodMenuAddControllerDto.getFoodType());
        assertEquals("testTemperature", foodMenuAddControllerDto.getTemperature());
        assertEquals("testMeat", foodMenuAddControllerDto.getMeatType());
        assertEquals("testVegetables", foodMenuAddControllerDto.getVegetables());
    }

    @Test
    void Post_계좌_업데이트_Dto_Convert() {
        FoodMenuAddControllerDto foodMenuAddControllerDto = new FoodMenuAddControllerDto("testName", 123456789, "testType", "testTemperature", "testMeat", "testVegetables");
        FoodMenuAddServiceDto foodMenuAddServiceDto = foodMenuAddControllerDto.convert();

        assertEquals("testName", foodMenuAddServiceDto.getName());
        assertEquals(123456789, foodMenuAddServiceDto.getPrice());
        assertEquals("testType", foodMenuAddServiceDto.getFoodType());
        assertEquals("testTemperature", foodMenuAddServiceDto.getTemperature());
        assertEquals("testMeat", foodMenuAddServiceDto.getMeatType());
        assertEquals("testVegetables", foodMenuAddServiceDto.getVegetables());
    }
}