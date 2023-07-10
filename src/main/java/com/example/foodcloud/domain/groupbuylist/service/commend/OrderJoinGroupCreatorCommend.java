package com.example.foodcloud.domain.groupbuylist.service.commend;

import lombok.Getter;

@Getter
public class OrderJoinGroupCreatorCommend {
    private final String location;
    private final Long restaurantId;

    public OrderJoinGroupCreatorCommend(String location, Long restaurantId) {
        this.location = location;
        this.restaurantId = restaurantId;
    }
}