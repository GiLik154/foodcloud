package com.example.foodcloud.domain.order.service.add;

import com.example.foodcloud.domain.order.service.dto.OrderMenuDto;

public interface OrderMenuAddService {
    void add(Long userId, OrderMenuDto orderMenuDto);
}
