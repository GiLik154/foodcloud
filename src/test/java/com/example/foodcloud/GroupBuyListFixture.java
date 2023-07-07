package com.example.foodcloud;

import com.example.foodcloud.domain.groupbuylist.domain.GroupBuyList;
import com.example.foodcloud.domain.restaurant.domain.Restaurant;
import com.example.foodcloud.domain.user.domain.User;

public class GroupBuyListFixture {
    private String location = "testLocation";

    private User user;

    private Restaurant restaurant;

    private GroupBuyListFixture(User user, Restaurant restaurant) {
        this.user = user;
        this.restaurant = restaurant;
    }

    public static GroupBuyListFixture fixture(User user, Restaurant restaurant) {
        return new GroupBuyListFixture(user, restaurant);
    }

    public GroupBuyListFixture location(String location) {
        this.location = location;
        return this;
    }

    public GroupBuyListFixture user(User user) {
        this.user = user;
        return this;
    }

    public GroupBuyListFixture restaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
        return this;
    }

    public GroupBuyList build() {
        return new GroupBuyList(this.location, this.user, this.restaurant);
    }
}
