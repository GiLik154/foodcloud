package com.example.foodcloud.domain.order.menu.service.add.dto;

import lombok.Getter;

@Getter
public class OrderMenuAddServiceDto {
    private final String location;
    private final int count;
    private final Long foodMenuId;
    private final Long orderJoinGroupId;

    public OrderMenuAddServiceDto(String location, int count, Long foodMenuId, Long orderJoinGroupId) {
        this.location = location;
        this.count = count;
        this.foodMenuId = foodMenuId;
        this.orderJoinGroupId = orderJoinGroupId;
    }
}
