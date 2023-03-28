package com.example.foodcloud.domain.order.menu.menu.service.update.payment;

import com.example.foodcloud.domain.payment.Payment;

public interface OrderMenuPaymentUpdateService {
    <T extends Payment> boolean update(Long orderMenuId, T payment);
}
