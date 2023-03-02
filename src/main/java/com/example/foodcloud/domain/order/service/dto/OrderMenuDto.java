package com.example.foodcloud.domain.order.service.dto;

import lombok.Getter;

@Getter
public class OrderMenuDto {
    private String location;
    private Long bankAccountId;
    private Long restaurantId;
    private Long foodMenuId;

    public OrderMenuDto(String location, Long bankAccountId, Long restaurantId, Long foodMenuId) {
        this.location = location;
        this.bankAccountId = bankAccountId;
        this.restaurantId = restaurantId;
        this.foodMenuId = foodMenuId;
    }
}
