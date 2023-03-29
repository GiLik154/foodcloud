package com.example.foodcloud.controller.core.foodmenu.dto;

import com.example.foodcloud.domain.foodmenu.service.add.dto.FoodMenuAddServiceDto;
import com.example.foodcloud.enums.foodmenu.FoodTypes;
import com.example.foodcloud.enums.foodmenu.MeatTypes;
import com.example.foodcloud.enums.foodmenu.Temperature;
import com.example.foodcloud.enums.foodmenu.Vegetables;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FoodMenuAddControllerDtoTest {
    @Test
    void 음식_추가_Dto() {
        FoodMenuAddControllerDto foodMenuAddControllerDto = new FoodMenuAddControllerDto("testName", 123456789, Temperature.COLD, FoodTypes.ADE, MeatTypes.BEEF, Vegetables.FEW);

        assertEquals("testName", foodMenuAddControllerDto.getName());
        assertEquals(123456789, foodMenuAddControllerDto.getPrice());
        assertEquals(Temperature.COLD, foodMenuAddControllerDto.getTemperature());
        assertEquals(FoodTypes.ADE, foodMenuAddControllerDto.getFoodTypes());
        assertEquals(MeatTypes.BEEF, foodMenuAddControllerDto.getMeatType());
        assertEquals(Vegetables.FEW, foodMenuAddControllerDto.getVegetables());
    }

    @Test
    void 음식_추가_Dto_Convert() {
        FoodMenuAddControllerDto foodMenuAddControllerDto = new FoodMenuAddControllerDto("testName", 123456789, Temperature.COLD, FoodTypes.ADE, MeatTypes.BEEF, Vegetables.FEW);
        FoodMenuAddServiceDto foodMenuAddServiceDto = foodMenuAddControllerDto.convert();

        assertEquals("testName", foodMenuAddServiceDto.getName());
        assertEquals(123456789, foodMenuAddServiceDto.getPrice());
        assertEquals(Temperature.COLD, foodMenuAddServiceDto.getTemperature());
        assertEquals(FoodTypes.ADE, foodMenuAddServiceDto.getFoodTypes());
        assertEquals(MeatTypes.BEEF, foodMenuAddServiceDto.getMeatType());
        assertEquals(Vegetables.FEW, foodMenuAddServiceDto.getVegetables());
    }
}