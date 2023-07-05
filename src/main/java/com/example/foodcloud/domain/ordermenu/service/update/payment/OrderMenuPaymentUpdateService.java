package com.example.foodcloud.domain.ordermenu.service.update.payment;

import com.example.foodcloud.domain.payment.domain.Payment;

public interface OrderMenuPaymentUpdateService {
    void update(Long orderMenuId, Payment payment);
}
