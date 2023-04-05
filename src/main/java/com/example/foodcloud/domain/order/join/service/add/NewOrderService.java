package com.example.foodcloud.domain.order.join.service.add;

import com.example.foodcloud.domain.order.join.service.add.dto.NewOrderServiceDto;

public interface NewOrderService {
    Long order(Long userId, NewOrderServiceDto newOrderServiceDto);
}
