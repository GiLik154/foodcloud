package com.example.foodcloud.controller.core.restaurant.restaurant.dto;

import com.example.foodcloud.domain.restaurant.service.commend.RestaurantRegisterCommend;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class RestaurantAddReq {
    @NotEmpty
    private final String name;
    @NotEmpty
    private final String location;
    @NotEmpty
    private final String openHours;
    @NotEmpty
    private final String closeHours;

    public RestaurantAddReq(String name, String location, String openHours, String closeHours) {
        this.name = name;
        this.location = location;
        this.openHours = openHours;
        this.closeHours = closeHours;
    }

    public RestaurantRegisterCommend convert(){
        return new RestaurantRegisterCommend(this.name, this.location, this.openHours + "-" + this.closeHours);
    }
}
