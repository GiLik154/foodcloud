package com.example.foodcloud.domain.order.main.service.add;

import com.example.foodcloud.domain.order.main.service.add.dto.NewOrderServiceDto;
import com.example.foodcloud.domain.order.main.service.add.dto.OrderMainAddServiceDto;

public interface NewOrderService {
    void order(Long userId, NewOrderServiceDto newOrderServiceDto);
}
