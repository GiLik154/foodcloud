package com.example.foodcloud.domain.order.menu.service.update.payment;

import com.example.foodcloud.domain.payment.Payment;

public interface OrderMenuPaymentUpdateService {
    <T extends Payment> boolean isUpdate(Long orderMenuId, T payment);
}
