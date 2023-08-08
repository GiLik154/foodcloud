package com.example.foodcloud.controller.core.restaurant.restaurant.dto;

import com.example.foodcloud.domain.restaurant.service.commend.RestaurantUpdaterCommend;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RestaurantUpdateReqTest {
    @Test
    void Post_식당_수정_Dto() {
        RestaurantUpdateReq restaurantUpdateReq = new RestaurantUpdateReq("testName", "testLocation", "testHours");

        assertEquals("testName", restaurantUpdateReq.getName());
        assertEquals("testLocation", restaurantUpdateReq.getLocation());
        assertEquals("testHours", restaurantUpdateReq.getBusinessHours());
    }

    @Test
    void Post_식당_수정_Dto_Convert() {
        RestaurantUpdateReq restaurantUpdateReq = new RestaurantUpdateReq("testName", "testLocation", "testHours");
        RestaurantUpdaterCommend restaurantUpdaterCommend = restaurantUpdateReq.convert();

        assertEquals("testName", restaurantUpdaterCommend.getName());
        assertEquals("testLocation", restaurantUpdaterCommend.getLocation());
        assertEquals("testHours", restaurantUpdaterCommend.getBusinessHours());
    }
}