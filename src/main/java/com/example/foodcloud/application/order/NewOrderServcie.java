package com.example.foodcloud.application.order;

import com.example.foodcloud.application.order.commend.NewOrderServiceCommend;
import com.example.foodcloud.domain.groupbuylist.service.OrderJoinGroupCreator;
import com.example.foodcloud.domain.ordermenu.service.OrderMenuCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
public class NewOrderServcie implements NewOrder {
    private final OrderJoinGroupCreator orderJoinGroupCreator;
    private final OrderMenuCreator orderMenuCreator;

    @Override
    public Long order(Long userId, NewOrderServiceCommend newOrderServiceCommend) {
        Long orderJoinGroupId = orderJoinGroupCreator.add(userId, newOrderServiceCommend.convertOrderJoinGroupDto());

        return orderMenuCreator.crate(userId, newOrderServiceCommend.convertOrderMenuDto(orderJoinGroupId));
    }
}
