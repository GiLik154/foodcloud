package com.example.foodcloud;

import com.example.foodcloud.domain.restaurant.domain.Restaurant;
import com.example.foodcloud.domain.user.domain.User;

public class RestaurantFixture {
    private String name = "testRestaurantName";
    private String location = "testLocation";
    private String businessHours = "testHours";
    private final User user;

    private RestaurantFixture(User user) {
        this.user = user;
    }

    public static RestaurantFixture fixture(User user) {
        return new RestaurantFixture(user);
    }

    public RestaurantFixture name(String name) {
        this.name = name;
        return this;
    }

    public RestaurantFixture location(String location) {
        this.location = location;
        return this;
    }

    public RestaurantFixture businessHours(String businessHours) {
        this.businessHours = businessHours;
        return this;
    }

    public Restaurant build() {
        return new Restaurant(this.name, this.location, this.businessHours, this.user);
    }
}