package com.example.foodcloud.controller.core.foodmenu.update.dto;

import com.example.foodcloud.controller.core.foodmenu.req.FoodMenuUpdaterReq;
import com.example.foodcloud.domain.foodmenu.service.commend.FoodMenuUpdaterCommend;
import com.example.foodcloud.enums.foodmenu.FoodTypes;
import com.example.foodcloud.enums.foodmenu.MeatTypes;
import com.example.foodcloud.enums.foodmenu.Temperature;
import com.example.foodcloud.enums.foodmenu.Vegetables;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FoodMenuUpdaterReqTest {
    @Test
    void Post_음식_메뉴_업데이트_Dto() {
        FoodMenuUpdaterReq foodMenuUpdaterReq = new FoodMenuUpdaterReq("testName", 123456789, Temperature.COLD, FoodTypes.ADE, MeatTypes.CHICKEN, Vegetables.FEW);

        assertEquals("testName", foodMenuUpdaterReq.getName());
        assertEquals(123456789, foodMenuUpdaterReq.getPrice());
        assertEquals(Temperature.COLD, foodMenuUpdaterReq.getTemperature());
        assertEquals(FoodTypes.ADE, foodMenuUpdaterReq.getFoodTypes());
        assertEquals(MeatTypes.CHICKEN, foodMenuUpdaterReq.getMeatType());
        assertEquals(Vegetables.FEW, foodMenuUpdaterReq.getVegetables());
    }

    @Test
    void Post_음식_메뉴_업데이트_Dto_Convert() {
        FoodMenuUpdaterReq foodMenuUpdaterReq = new FoodMenuUpdaterReq("testName", 123456789, Temperature.COLD, FoodTypes.ADE, MeatTypes.CHICKEN, Vegetables.FEW);
        FoodMenuUpdaterCommend foodMenuUpdaterCommend = foodMenuUpdaterReq.convert();

        assertEquals("testName", foodMenuUpdaterCommend.getName());
        assertEquals(123456789, foodMenuUpdaterCommend.getPrice());
        assertEquals(Temperature.COLD, foodMenuUpdaterCommend.getTemperature());
        assertEquals(FoodTypes.ADE, foodMenuUpdaterCommend.getFoodTypes());
        assertEquals(MeatTypes.CHICKEN, foodMenuUpdaterCommend.getMeatType());
        assertEquals(Vegetables.FEW, foodMenuUpdaterCommend.getVegetables());
    }
}