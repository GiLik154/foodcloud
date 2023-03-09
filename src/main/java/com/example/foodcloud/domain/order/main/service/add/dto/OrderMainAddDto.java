package com.example.foodcloud.domain.order.main.service.add.dto;

import lombok.Getter;

@Getter
public class OrderMainAddDto {
    private String location;
    private Long bankAccountId;
    private Long restaurantId;
    private String result;

    public OrderMainAddDto(String location, Long bankAccountId, Long restaurantId, String result) {
        this.location = location;
        this.bankAccountId = bankAccountId;
        this.restaurantId = restaurantId;
        this.result = result;
    }
}
