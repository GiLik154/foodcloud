package com.example.foodcloud.domain.ordermain.service.add;

import com.example.foodcloud.domain.ordermain.service.dto.OrderMainDto;

public interface OrderMainAddService {
    void add(Long userId, OrderMainDto orderMainId);
}
