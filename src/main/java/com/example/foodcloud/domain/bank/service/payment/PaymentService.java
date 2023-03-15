package com.example.foodcloud.domain.bank.service.payment;

public interface PaymentService {
    String pay(String bank, Long userId, Long orderMenuId, Long bankAccountId, int price);
    String refund(Long userId, Long orderMenuId, Long bankAccountId, int price);
}
