package com.example.foodcloud.domain.order.main.service.add;

import com.example.foodcloud.domain.order.main.service.add.dto.OrderMainAddDto;

public interface OrderMainAddService {
    void add(Long userId, OrderMainAddDto orderMainAddDto);
}
