package com.example.foodcloud.domain.restaurant.service.add.dto;

import lombok.Getter;

@Getter
public class RestaurantAddServiceDto {
    private String name;
    private String location;
    private String businessHours;

    public RestaurantAddServiceDto(String name, String location, String businessHours) {
        this.name = name;
        this.location = location;
        this.businessHours = businessHours;
    }
}