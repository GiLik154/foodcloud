package com.example.foodcloud.domain.order.main.service.add;

import com.example.foodcloud.domain.order.main.service.dto.OrderMainDto;

public interface OrderMainAddService {
    void add(Long userId, OrderMainDto orderMainDto);
}
