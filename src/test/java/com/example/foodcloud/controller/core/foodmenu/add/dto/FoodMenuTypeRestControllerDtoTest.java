package com.example.foodcloud.controller.core.foodmenu.add.dto;

import com.example.foodcloud.controller.core.foodmenu.req.FoodMenCreateReq;
import com.example.foodcloud.domain.foodmenu.service.commend.FoodMenuCreatorCommend;
import com.example.foodcloud.enums.foodmenu.FoodTypes;
import com.example.foodcloud.enums.foodmenu.MeatTypes;
import com.example.foodcloud.enums.foodmenu.Temperature;
import com.example.foodcloud.enums.foodmenu.Vegetables;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FoodMenuTypeRestControllerDtoTest {
    @Test
    void 음식_추가_Dto() {
        FoodMenCreateReq foodMenCreateReq = new FoodMenCreateReq("testName", 123456789, Temperature.COLD, FoodTypes.ADE, MeatTypes.BEEF, Vegetables.FEW);

        assertEquals("testName", foodMenCreateReq.getName());
        assertEquals(123456789, foodMenCreateReq.getPrice());
        assertEquals(Temperature.COLD, foodMenCreateReq.getTemperature());
        assertEquals(FoodTypes.ADE, foodMenCreateReq.getFoodTypes());
        assertEquals(MeatTypes.BEEF, foodMenCreateReq.getMeatType());
        assertEquals(Vegetables.FEW, foodMenCreateReq.getVegetables());
    }

    @Test
    void 음식_추가_Dto_Convert() {
        FoodMenCreateReq foodMenCreateReq = new FoodMenCreateReq("testName", 123456789, Temperature.COLD, FoodTypes.ADE, MeatTypes.BEEF, Vegetables.FEW);
        FoodMenuCreatorCommend foodMenuCreatorCommend = foodMenCreateReq.convert();

        assertEquals("testName", foodMenuCreatorCommend.getName());
        assertEquals(123456789, foodMenuCreatorCommend.getPrice());
        assertEquals(Temperature.COLD, foodMenuCreatorCommend.getTemperature());
        assertEquals(FoodTypes.ADE, foodMenuCreatorCommend.getFoodTypes());
        assertEquals(MeatTypes.BEEF, foodMenuCreatorCommend.getMeatType());
        assertEquals(Vegetables.FEW, foodMenuCreatorCommend.getVegetables());
    }
}