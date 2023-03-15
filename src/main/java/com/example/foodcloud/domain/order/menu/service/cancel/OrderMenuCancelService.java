package com.example.foodcloud.domain.order.menu.service.cancel;

public interface OrderMenuCancelService {
    boolean isCancel(Long userId, Long orderMenuId);
}
