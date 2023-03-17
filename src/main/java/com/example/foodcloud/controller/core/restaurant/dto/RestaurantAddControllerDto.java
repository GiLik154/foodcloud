package com.example.foodcloud.controller.core.restaurant.dto;

import com.example.foodcloud.domain.restaurant.service.add.dto.RestaurantAddServiceDto;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class RestaurantAddControllerDto {
    @NotEmpty
    private final String name;
    @NotEmpty
    private final String location;
    @NotEmpty
    private final String businessHours;

    public RestaurantAddControllerDto(String name, String location, String businessHours) {
        this.name = name;
        this.location = location;
        this.businessHours = businessHours;
    }

    public RestaurantAddServiceDto convert(){
        return new RestaurantAddServiceDto(this.name, this.location, this.businessHours);
    }
}
