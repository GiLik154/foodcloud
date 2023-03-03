package com.example.foodcloud.domain.ordermain.service.dto;

import lombok.Getter;

@Getter
public class OrderMainDto {
    private String location;
    private Long bankAccountId;
    private Long restaurantId;
    private String result;

    public OrderMainDto(String location, Long bankAccountId, Long restaurantId, String result) {
        this.location = location;
        this.bankAccountId = bankAccountId;
        this.restaurantId = restaurantId;
        this.result = result;
    }
}
