package com.example.foodcloud.controller.core.order.req;

import com.example.foodcloud.domain.ordermenu.service.commend.OrderMenuCreatorCommend;
import lombok.Getter;

@Getter
public class OrderJoinReq {
    private final String location;
    private final int count;
    private final Long foodMenuId;

    public OrderJoinReq(String location, int count, Long foodMenuId) {
        this.location = location;
        this.count = count;
        this.foodMenuId = foodMenuId;
    }

    public OrderMenuCreatorCommend convert(Long orderJoinGroupId) {
        return new OrderMenuCreatorCommend(this.location, this.count, this.foodMenuId, orderJoinGroupId);
    }
}