package com.example.foodcloud.domain.order.main.service.add.dto;

import com.example.foodcloud.domain.order.menu.service.add.dto.OrderMenuAddServiceDto;
import lombok.Getter;

@Getter
public class NewOrderServiceDto {
    private final String location;
    private final Long bankAccountId;
    private final Long restaurantId;
    private final Long foodMenuId;
    private final int count;

    public NewOrderServiceDto(String location, Long bankAccountId, Long restaurantId, Long foodMenuId, int count) {
        this.location = location;
        this.bankAccountId = bankAccountId;
        this.restaurantId = restaurantId;
        this.foodMenuId = foodMenuId;
        this.count = count;
    }

    public OrderMainAddServiceDto convertMainDto() {
        return new OrderMainAddServiceDto(this.location, this.bankAccountId, this.restaurantId);
    }

    public OrderMenuAddServiceDto convertMenuDto(Long orderMainId) {
        return new OrderMenuAddServiceDto(this.location, this.count, this.bankAccountId, this.foodMenuId, orderMainId);
    }
}
