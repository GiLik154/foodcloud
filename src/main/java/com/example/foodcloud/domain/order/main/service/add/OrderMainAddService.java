package com.example.foodcloud.domain.order.main.service.add;

import com.example.foodcloud.domain.order.main.service.add.dto.OrderMainAddServiceDto;

public interface OrderMainAddService {
    Long add(Long userId, OrderMainAddServiceDto orderMainAddServiceDto);
}
