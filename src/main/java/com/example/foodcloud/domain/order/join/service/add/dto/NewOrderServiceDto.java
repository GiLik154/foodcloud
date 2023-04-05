package com.example.foodcloud.domain.order.join.service.add.dto;

import com.example.foodcloud.domain.order.menu.service.add.dto.OrderMenuAddServiceDto;
import lombok.Getter;

@Getter
public class NewOrderServiceDto {
    private final String location;
    private final int count;
    private final Long restaurantId;
    private final Long foodMenuId;

    public NewOrderServiceDto(String location, int count, Long restaurantId, Long foodMenuId) {
        this.location = location;
        this.count = count;
        this.restaurantId = restaurantId;
        this.foodMenuId = foodMenuId;
    }

    public OrderJoinGroupAddServiceDto convertMainDto() {
        return new OrderJoinGroupAddServiceDto(this.location, this.restaurantId);
    }

    public OrderMenuAddServiceDto convertMenuDto(Long orderJoinGroupId) {
        return new OrderMenuAddServiceDto(this.location, this.count, this.foodMenuId, orderJoinGroupId);
    }
}
