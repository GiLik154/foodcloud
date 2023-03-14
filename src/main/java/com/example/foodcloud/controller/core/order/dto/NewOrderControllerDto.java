package com.example.foodcloud.controller.core.order.dto;

import com.example.foodcloud.domain.order.main.service.add.dto.NewOrderServiceDto;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
public class NewOrderControllerDto {
    @NotEmpty
    private final String location;
    @NotNull
    private final Long bankAccountId;
    @NotNull
    private final Long restaurantId;
    @NotNull
    private final Long foodMenuId;
    @NotNull
    private final int count;

    public NewOrderControllerDto(String location, Long bankAccountId, Long restaurantId, int count, Long foodMenuId) {
        this.location = location;
        this.bankAccountId = bankAccountId;
        this.restaurantId = restaurantId;
        this.foodMenuId = foodMenuId;
        this.count = count;
    }

    public NewOrderServiceDto convert() {
        return new NewOrderServiceDto(this.location, this.bankAccountId, this.restaurantId, this.foodMenuId, this.count);
    }
}