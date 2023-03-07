package com.example.foodcloud.domain.order.menu.service.add;

import com.example.foodcloud.domain.order.menu.service.dto.OrderMenuDto;

public interface OrderMenuAddService {
     void add(Long userId, OrderMenuDto orderMenuDto);
}
