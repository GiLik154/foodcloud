package com.example.foodcloud.domain.order.main.service.add.dto;

import com.example.foodcloud.domain.order.menu.menu.service.add.dto.OrderMenuAddServiceDto;
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

    public OrderMainAddServiceDto convertMainDto() {
        return new OrderMainAddServiceDto(this.location, this.restaurantId);
    }

    public OrderMenuAddServiceDto convertMenuDto(Long orderMainId) {
        return new OrderMenuAddServiceDto(this.location, this.count, this.foodMenuId, orderMainId);
    }
}
