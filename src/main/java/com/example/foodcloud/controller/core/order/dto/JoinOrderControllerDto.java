package com.example.foodcloud.controller.core.order.dto;

import com.example.foodcloud.domain.order.menu.service.add.dto.OrderMenuAddServiceDto;
import lombok.Getter;

@Getter
public class JoinOrderControllerDto {
    private final String location;
    private final int count;
    private final Long foodMenuId;
    private final Long orderMainId;

    public JoinOrderControllerDto(String location, int count, Long foodMenuId, Long orderMainId) {
        this.location = location;
        this.count = count;
        this.foodMenuId = foodMenuId;
        this.orderMainId = orderMainId;
    }

    public OrderMenuAddServiceDto convert() {
        return new OrderMenuAddServiceDto(this.location, this.count, this.foodMenuId, this.orderMainId);
    }
}