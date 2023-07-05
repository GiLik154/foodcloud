package com.example.foodcloud.domain.restaurant.domain;

import com.example.foodcloud.UserFixture;
import com.example.foodcloud.domain.user.domain.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    private final User user = UserFixture.fixture().build();

    @Test
    void 최상의_생성_테스트() {
        Restaurant restaurant = new Restaurant("testName", "testLocation", "testHours", user);

        assertEquals("testName", restaurant.getName());
        assertEquals("testLocation", restaurant.getLocation());
        assertEquals("testHours", restaurant.getBusinessHours());
        assertEquals(user, restaurant.getUser());
    }

    @Test
    void 최상의_업데이트_테스트() {
        Restaurant restaurant = new Restaurant("testName", "testLocation", "testHours", user);

        restaurant.update("newName", "newLocation", "newHours");

        assertEquals("newName", restaurant.getName());
        assertEquals("newLocation", restaurant.getLocation());
        assertEquals("newHours", restaurant.getBusinessHours());
    }

    @Test
    void 식당의_조회수_증가() {
        Restaurant restaurant = new Restaurant("testName", "testLocation", "testHours", user);
        restaurant.incrementOrderCount();

        assertEquals(1, restaurant.getOrderCount());
    }

    @Test
    void 식당의_조회수_증가_3회() {
        Restaurant restaurant = new Restaurant("testName", "testLocation", "testHours", user);
        restaurant.incrementOrderCount();
        restaurant.incrementOrderCount();
        restaurant.incrementOrderCount();

        assertEquals(3, restaurant.getOrderCount());
    }
}