package com.example.foodcloud.controller.core.restaurant.restaurant.dto;

import com.example.foodcloud.domain.restaurant.service.commend.RestaurantRegisterCommend;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class RestaurantAddReqTest {
    @Test
    void Post_식당_추가_Dto() {
        RestaurantAddReq restaurantAddReq = new RestaurantAddReq("testName", "testLocation", "testOpenHours", "testCloseHours");

        assertEquals("testName", restaurantAddReq.getName());
        assertEquals("testLocation", restaurantAddReq.getLocation());
        assertEquals("testOpenHours", restaurantAddReq.getOpenHours());
        assertEquals("testCloseHours", restaurantAddReq.getCloseHours());
    }

    @Test
    void Post_식당_추가_Dto_Convert() {
        RestaurantAddReq restaurantAddReq = new RestaurantAddReq("testName", "testLocation", "testOpenHours", "testCloseHours");
        RestaurantRegisterCommend restaurantRegisterCommend = restaurantAddReq.convert();

        assertEquals("testName", restaurantRegisterCommend.getName());
        assertEquals("testLocation", restaurantRegisterCommend.getLocation());
        assertEquals("testOpenHours-testCloseHours", restaurantRegisterCommend.getBusinessHours());
    }
}