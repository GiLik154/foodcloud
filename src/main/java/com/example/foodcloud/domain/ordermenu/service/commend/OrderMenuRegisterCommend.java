package com.example.foodcloud.domain.ordermenu.service.commend;

import lombok.Getter;

@Getter
public class OrderMenuRegisterCommend {
    private final String location;
    private final int count;
    private final Long foodMenuId;
    private final Long orderJoinGroupId;

    public OrderMenuRegisterCommend(String location, int count, Long foodMenuId, Long orderJoinGroupId) {
        this.location = location;
        this.count = count;
        this.foodMenuId = foodMenuId;
        this.orderJoinGroupId = orderJoinGroupId;
    }
}
