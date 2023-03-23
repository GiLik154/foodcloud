package com.example.foodcloud.controller.core.foodmenu.dto;

import com.example.foodcloud.domain.foodmenu.service.update.dto.FoodMenuUpdateServiceDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FoodMenuUpdateControllerDtoTest {
    @Test
    void Post_음식_메뉴_업데이트_Dto() {
        FoodMenuUpdateControllerDto foodMenuUpdateControllerDto = new FoodMenuUpdateControllerDto("testName", 123456789, "testType", "testTemperature", "testMeat", "testVegetables");

        assertEquals("testName", foodMenuUpdateControllerDto.getName());
        assertEquals(123456789, foodMenuUpdateControllerDto.getPrice());
        assertEquals("testType", foodMenuUpdateControllerDto.getFoodType());
        assertEquals("testTemperature", foodMenuUpdateControllerDto.getTemperature());
        assertEquals("testMeat", foodMenuUpdateControllerDto.getMeatType());
        assertEquals("testVegetables", foodMenuUpdateControllerDto.getVegetables());
    }

    @Test
    void Post_음식_메뉴_업데이트_Dto_Convert() {
        FoodMenuUpdateControllerDto foodMenuUpdateControllerDto = new FoodMenuUpdateControllerDto("testName", 123456789, "testType", "testTemperature", "testMeat", "testVegetables");
        FoodMenuUpdateServiceDto foodMenuUpdateServiceDto = foodMenuUpdateControllerDto.convert();

        assertEquals("testName", foodMenuUpdateServiceDto.getName());
        assertEquals(123456789, foodMenuUpdateServiceDto.getPrice());
        assertEquals("testType", foodMenuUpdateServiceDto.getFoodType());
        assertEquals("testTemperature", foodMenuUpdateServiceDto.getTemperature());
        assertEquals("testMeat", foodMenuUpdateServiceDto.getMeatType());
        assertEquals("testVegetables", foodMenuUpdateServiceDto.getVegetables());
    }
}