package com.example.foodcloud.domain.restaurant.service.update.dto;

import lombok.Getter;

@Getter
public class RestaurantUpdateServiceDto {
    private String name;
    private String location;
    private String businessHours;

    public RestaurantUpdateServiceDto(String name, String location, String businessHours) {
        this.name = name;
        this.location = location;
        this.businessHours = businessHours;
    }
}
