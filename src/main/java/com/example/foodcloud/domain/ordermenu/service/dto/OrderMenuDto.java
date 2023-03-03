package com.example.foodcloud.domain.ordermenu.service.dto;

import lombok.Getter;

@Getter
public class OrderMenuDto {
    private String location;
    private int count;
    private Long bankAccountId;
    private Long foodMenuId;
    private Long orderMainId;

    public OrderMenuDto(String location, int count, Long bankAccountId, Long foodMenuId, Long orderMainId) {
        this.location = location;
        this.count = count;
        this.bankAccountId = bankAccountId;
        this.foodMenuId = foodMenuId;
        this.orderMainId = orderMainId;
    }
}
