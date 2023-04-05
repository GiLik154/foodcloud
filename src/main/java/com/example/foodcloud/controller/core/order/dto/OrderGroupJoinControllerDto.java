package com.example.foodcloud.controller.core.order.dto;

import com.example.foodcloud.domain.order.menu.service.add.dto.OrderMenuAddServiceDto;
import lombok.Getter;

@Getter
public class OrderGroupJoinControllerDto {
    private final String location;
    private final int count;
    private final Long foodMenuId;

    public OrderGroupJoinControllerDto(String location, int count, Long foodMenuId) {
        this.location = location;
        this.count = count;
        this.foodMenuId = foodMenuId;
    }

    public OrderMenuAddServiceDto convert(Long orderJoinGroupId) {
        return new OrderMenuAddServiceDto(this.location, this.count, this.foodMenuId, orderJoinGroupId);
    }
}