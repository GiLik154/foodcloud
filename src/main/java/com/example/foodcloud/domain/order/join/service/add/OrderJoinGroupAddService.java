package com.example.foodcloud.domain.order.join.service.add;

import com.example.foodcloud.domain.order.join.service.add.dto.OrderJoinGroupAddServiceDto;

public interface OrderJoinGroupAddService {
    Long add(Long userId, OrderJoinGroupAddServiceDto orderJoinGroupAddServiceDto);
}
