package com.example.foodcloud.domain.order.menu.service.add;

import com.example.foodcloud.domain.order.menu.service.add.dto.OrderMenuAddServiceDto;

public interface OrderMenuAddService {
     void add(Long userId, OrderMenuAddServiceDto orderMenuAddServiceDto);
}
