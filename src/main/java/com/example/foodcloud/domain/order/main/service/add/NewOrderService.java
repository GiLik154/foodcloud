package com.example.foodcloud.domain.order.main.service.add;

import com.example.foodcloud.domain.order.main.service.add.dto.NewOrderServiceDto;

public interface NewOrderService {
    void order(Long userId, NewOrderServiceDto newOrderServiceDto);
}
