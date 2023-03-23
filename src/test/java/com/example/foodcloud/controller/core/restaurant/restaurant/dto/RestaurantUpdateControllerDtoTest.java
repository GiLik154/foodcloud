package com.example.foodcloud.controller.core.restaurant.restaurant.dto;

import com.example.foodcloud.domain.restaurant.service.update.dto.RestaurantUpdateServiceDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantUpdateControllerDtoTest {
    @Test
    void Post_식당_수정_Dto() {
        RestaurantUpdateControllerDto restaurantUpdateControllerDto = new RestaurantUpdateControllerDto("testName", "testLocation", "testHours");

        assertEquals("testName", restaurantUpdateControllerDto.getName());
        assertEquals("testLocation", restaurantUpdateControllerDto.getLocation());
        assertEquals("testHours", restaurantUpdateControllerDto.getBusinessHours());
    }

    @Test
    void Post_식당_수정_Dto_Convert() {
        RestaurantUpdateControllerDto restaurantUpdateControllerDto = new RestaurantUpdateControllerDto("testName", "testLocation", "testHours");
        RestaurantUpdateServiceDto restaurantUpdateServiceDto = restaurantUpdateControllerDto.convert();

        assertEquals("testName", restaurantUpdateServiceDto.getName());
        assertEquals("testLocation", restaurantUpdateServiceDto.getLocation());
        assertEquals("testHours", restaurantUpdateServiceDto.getBusinessHours());
    }
}