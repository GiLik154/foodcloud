package com.example.foodcloud.controller.core.restaurant.restaurant.dto;

import com.example.foodcloud.domain.restaurant.service.commend.RestaurantUpdaterCommend;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class RestaurantUpdateReq {
    @NotEmpty
    private final String name;
    @NotEmpty
    private final String location;
    @NotEmpty
    private final String businessHours;

    public RestaurantUpdateReq(String name, String location, String businessHours) {
        this.name = name;
        this.location = location;
        this.businessHours = businessHours;
    }

    public RestaurantUpdaterCommend convert() {
        return new RestaurantUpdaterCommend(this.name, this.location, this.businessHours);
    }
}
