package com.example.foodcloud.controller.core.order.dto;

import com.example.foodcloud.domain.order.main.service.add.dto.NewOrderServiceDto;
import com.example.foodcloud.domain.order.main.service.add.dto.OrderMainAddServiceDto;
import com.example.foodcloud.domain.order.menu.service.add.dto.OrderMenuAddServiceDto;
import lombok.Getter;

@Getter
public class NewOrderControllerDto {
    private final String location;
    private final Long bankAccountId;
    private final Long restaurantId;
    private final Long foodMenuId;
    private final int count;

    public NewOrderControllerDto(String location, Long bankAccountId, Long restaurantId, int count, Long foodMenuId) {
        this.location = location;
        this.bankAccountId = bankAccountId;
        this.restaurantId = restaurantId;
        this.foodMenuId = foodMenuId;
        this.count = count;
    }

    public NewOrderServiceDto convert(){
        return new NewOrderServiceDto(this.location, this.bankAccountId, this.restaurantId, this.foodMenuId, this.count);
    }
}