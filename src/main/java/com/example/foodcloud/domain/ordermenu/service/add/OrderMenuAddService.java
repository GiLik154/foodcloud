package com.example.foodcloud.domain.ordermenu.service.add;

import com.example.foodcloud.domain.ordermenu.service.dto.OrderMenuDto;

public interface OrderMenuAddService {
     void add(Long userId, OrderMenuDto orderMenuDto);
}
