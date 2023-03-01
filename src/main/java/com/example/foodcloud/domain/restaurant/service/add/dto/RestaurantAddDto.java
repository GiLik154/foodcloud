package com.example.foodcloud.domain.restaurant.service.add.dto;

import lombok.Getter;

@Getter
public class RestaurantAddDto {
    private String name;
    private String location;
    private int orderCount;
    private String businessHours;

    public RestaurantAddDto(String name, String location, int orderCount, String businessHours) {
        this.name = name;
        this.location = location;
        this.orderCount = orderCount;
        this.businessHours = businessHours;
    }
}