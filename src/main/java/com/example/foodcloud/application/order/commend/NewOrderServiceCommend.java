package com.example.foodcloud.application.order.commend;

import com.example.foodcloud.domain.groupbuylist.service.commend.OrderJoinGroupCreatorCommend;
import com.example.foodcloud.domain.ordermenu.service.commend.OrderMenuRegisterCommend;
import lombok.Getter;

@Getter
public class NewOrderServiceCommend {
    private final String location;
    private final int count;
    private final Long restaurantId;
    private final Long foodMenuId;

    public NewOrderServiceCommend(String location, int count, Long restaurantId, Long foodMenuId) {
        this.location = location;
        this.count = count;
        this.restaurantId = restaurantId;
        this.foodMenuId = foodMenuId;
    }

    public OrderJoinGroupCreatorCommend convertOrderJoinGroupDto() {
        return new OrderJoinGroupCreatorCommend(this.location, this.restaurantId);
    }

    public OrderMenuRegisterCommend convertOrderMenuDto(Long orderJoinGroupId) {
        return new OrderMenuRegisterCommend(this.location, this.count, this.foodMenuId, orderJoinGroupId);
    }
}
