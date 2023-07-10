package com.example.foodcloud.controller.core.foodmenu.add.dto;

import com.example.foodcloud.controller.core.foodmenu.dto.FoodMenuAddControllerDto;
import com.example.foodcloud.domain.foodmenu.service.commend.FoodMenuCreatorCommend;
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
        FoodMenuCreatorCommend foodMenuCreatorCommend = foodMenuAddControllerDto.convert();

        assertEquals("testName", foodMenuCreatorCommend.getName());
        assertEquals(123456789, foodMenuCreatorCommend.getPrice());
        assertEquals(Temperature.COLD, foodMenuCreatorCommend.getTemperature());
        assertEquals(FoodTypes.ADE, foodMenuCreatorCommend.getFoodTypes());
        assertEquals(MeatTypes.BEEF, foodMenuCreatorCommend.getMeatType());
        assertEquals(Vegetables.FEW, foodMenuCreatorCommend.getVegetables());
    }
}