package com.example.foodcloud.controller.core.restaurant.restaurant.add.dto;

import com.example.foodcloud.controller.core.restaurant.restaurant.dto.RestaurantAddControllerDto;
import com.example.foodcloud.domain.restaurant.service.commend.RestaurantRegisterCommend;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class RestaurantAddControllerDtoTest {
    @Test
    void Post_식당_추가_Dto() {
        RestaurantAddControllerDto restaurantAddControllerDto = new RestaurantAddControllerDto("testName", "testLocation", "testOpenHours", "testCloseHours");

        assertEquals("testName", restaurantAddControllerDto.getName());
        assertEquals("testLocation", restaurantAddControllerDto.getLocation());
        assertEquals("testOpenHours", restaurantAddControllerDto.getOpenHours());
        assertEquals("testCloseHours", restaurantAddControllerDto.getCloseHours());
    }

    @Test
    void Post_식당_추가_Dto_Convert() {
        RestaurantAddControllerDto restaurantAddControllerDto = new RestaurantAddControllerDto("testName", "testLocation", "testOpenHours", "testCloseHours");
        RestaurantRegisterCommend restaurantRegisterCommend = restaurantAddControllerDto.convert();

        assertEquals("testName", restaurantRegisterCommend.getName());
        assertEquals("testLocation", restaurantRegisterCommend.getLocation());
        assertEquals("testOpenHours-testCloseHours", restaurantRegisterCommend.getBusinessHours());
    }
}