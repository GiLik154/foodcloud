package com.example.foodcloud.domain.order.join.service.add.dto;

import lombok.Getter;

@Getter
public class OrderJoinGroupAddServiceDto {
    private final String location;
    private final Long restaurantId;

    public OrderJoinGroupAddServiceDto(String location, Long restaurantId) {
        this.location = location;
        this.restaurantId = restaurantId;
    }
}