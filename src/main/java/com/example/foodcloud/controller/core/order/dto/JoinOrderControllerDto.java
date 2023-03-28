package com.example.foodcloud.controller.core.order.dto;

import com.example.foodcloud.domain.order.menu.menu.service.add.dto.OrderMenuAddServiceDto;
import lombok.Getter;

@Getter
public class JoinOrderControllerDto {
    private final String location;
    private final int count;
    private final Long foodMenuId;

    public JoinOrderControllerDto(String location, int count, Long foodMenuId) {
        this.location = location;
        this.count = count;
        this.foodMenuId = foodMenuId;
    }

    public OrderMenuAddServiceDto convert(Long orderMainId) {
        return new OrderMenuAddServiceDto(this.location, this.count, this.foodMenuId, orderMainId);
    }
}