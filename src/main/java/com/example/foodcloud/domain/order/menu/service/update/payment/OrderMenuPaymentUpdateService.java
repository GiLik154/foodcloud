package com.example.foodcloud.domain.order.menu.service.update.payment;

import com.example.foodcloud.domain.payment.domain.Payment;

public interface OrderMenuPaymentUpdateService {
    void update(Long orderMenuId, Payment payment);
}
