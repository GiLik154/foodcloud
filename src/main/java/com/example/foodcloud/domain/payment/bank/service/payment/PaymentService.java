package com.example.foodcloud.domain.payment.bank.service.payment;

import com.example.foodcloud.domain.order.menu.domain.OrderMenu;

public interface PaymentService {
    String pay(Long userId, Long orderMenuId, Long bankAccountId, int price);
    String refund(Long userId, OrderMenu orderMenu);
}
