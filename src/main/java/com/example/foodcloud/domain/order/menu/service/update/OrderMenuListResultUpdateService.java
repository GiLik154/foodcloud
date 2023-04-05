package com.example.foodcloud.domain.order.menu.service.update;

import com.example.foodcloud.enums.OrderResult;

public interface OrderMenuListResultUpdateService {
    void update(Long orderMenuId, OrderResult orderResult);
}
