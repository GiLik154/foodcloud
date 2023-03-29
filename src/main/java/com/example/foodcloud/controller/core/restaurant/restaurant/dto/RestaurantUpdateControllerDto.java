package com.example.foodcloud.controller.core.restaurant.restaurant.dto;

import com.example.foodcloud.domain.restaurant.service.update.dto.RestaurantUpdateServiceDto;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class RestaurantUpdateControllerDto {
    @NotEmpty
    private final String name;
    @NotEmpty
    private final String location;
    @NotEmpty
    private final String businessHours;

    public RestaurantUpdateControllerDto(String name, String location, String businessHours) {
        this.name = name;
        this.location = location;
        this.businessHours = businessHours;
    }

    public RestaurantUpdateServiceDto convert() {
        return new RestaurantUpdateServiceDto(this.name, this.location, this.businessHours);
    }
}
