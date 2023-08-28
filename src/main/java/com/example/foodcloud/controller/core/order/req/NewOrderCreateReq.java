package com.example.foodcloud.controller.core.order.req;

import com.example.foodcloud.application.order.commend.NewOrderServiceCommend;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
public class NewOrderCreateReq {
    @NotEmpty
    private final String location;
    @NotNull
    private final int count;
    private final Long restaurantId;
    @NotNull
    private final Long foodMenuId;

    @NotNull

    public NewOrderCreateReq(String location, int count, Long restaurantId, Long foodMenuId) {
        this.location = location;
        this.count = count;
        this.restaurantId = restaurantId;
        this.foodMenuId = foodMenuId;
    }

    public NewOrderServiceCommend convert() {
        return new NewOrderServiceCommend(this.location, this.count, this.restaurantId, this.foodMenuId);
    }
}