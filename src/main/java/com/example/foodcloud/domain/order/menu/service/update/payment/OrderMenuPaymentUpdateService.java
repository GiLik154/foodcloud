package com.example.foodcloud.domain.order.menu.service.update.payment;

public interface OrderMenuPaymentUpdateService {
    boolean isUpdate(Long orderMenuId, Object payment);
}
