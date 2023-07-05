package com.example.foodcloud;

import com.example.foodcloud.domain.restaurant.domain.Restaurant;
import com.example.foodcloud.domain.user.domain.User;

public class RestaurantFixtures {
    private String name = "testRestaurantName";
    private String location = "testLocation";
    private String businessHours = "testHours";
    private User user;

    private RestaurantFixtures(User user) {
        this.user = user;
    }

    public static RestaurantFixtures fixtures(User user) {
        return new RestaurantFixtures(user);
    }

    public RestaurantFixtures name(String name) {
        this.name = name;
        return this;
    }

    public RestaurantFixtures location(String location) {
        this.location = location;
        return this;
    }

    public RestaurantFixtures businessHours(String businessHours) {
        this.businessHours = businessHours;
        return this;
    }

    public RestaurantFixtures user(User user) {
        this.user = user;
        return this;
    }

    public Restaurant build() {
        return new Restaurant(this.name, this.location, this.businessHours, this.user);
    }
}