package com.example.foodcloud.domain.order.main.service.add.dto;

import lombok.Getter;

@Getter
public class OrderMainAddServiceDto {
    private final String location;
    private final Long restaurantId;

    public OrderMainAddServiceDto(String location, Long restaurantId) {
        this.location = location;
        this.restaurantId = restaurantId;
    }
}
