package com.example.foodcloud.controller.core.restaurant.dto;

import com.example.foodcloud.domain.restaurant.service.update.dto.RestaurantUpdateServiceDto;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class RestaurantUpdateControllerDto {
    @NotEmpty
    private String name;
    @NotEmpty
    private String location;
    @NotEmpty
    private String businessHours;

    public RestaurantUpdateControllerDto(String name, String location, String businessHours) {
        this.name = name;
        this.location = location;
        this.businessHours = businessHours;
    }

    public RestaurantUpdateServiceDto convert() {
        return new RestaurantUpdateServiceDto(this.name, this.location, this.businessHours);
    }
}
