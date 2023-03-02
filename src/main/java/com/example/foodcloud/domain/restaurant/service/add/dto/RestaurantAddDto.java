package com.example.foodcloud.domain.restaurant.service.add.dto;

import lombok.Getter;

@Getter
public class RestaurantAddDto {
    private String name;
    private String location;
    private String businessHours;

    public RestaurantAddDto(String name, String location, String businessHours) {
        this.name = name;
        this.location = location;
        this.businessHours = businessHours;
    }
}