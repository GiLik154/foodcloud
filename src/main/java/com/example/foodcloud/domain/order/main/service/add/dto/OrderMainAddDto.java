package com.example.foodcloud.domain.order.main.service.add.dto;

import lombok.Getter;

@Getter
public class OrderMainAddDto {
    private final String location;
    private final Long bankAccountId;
    private final Long restaurantId;
    private final String result;

    public OrderMainAddDto(String location, Long bankAccountId, Long restaurantId, String result) {
        this.location = location;
        this.bankAccountId = bankAccountId;
        this.restaurantId = restaurantId;
        this.result = result;
    }
}
