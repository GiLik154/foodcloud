package com.example.foodcloud.controller.core.restaurant.restaurant.add.dto;

import com.example.foodcloud.controller.core.restaurant.restaurant.dto.RestaurantAddControllerDto;
import com.example.foodcloud.domain.restaurant.service.add.dto.RestaurantAddServiceDto;
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
        RestaurantAddServiceDto restaurantAddServiceDto = restaurantAddControllerDto.convert();

        assertEquals("testName", restaurantAddServiceDto.getName());
        assertEquals("testLocation", restaurantAddServiceDto.getLocation());
        assertEquals("testOpenHours-testCloseHours", restaurantAddServiceDto.getBusinessHours());
    }
}