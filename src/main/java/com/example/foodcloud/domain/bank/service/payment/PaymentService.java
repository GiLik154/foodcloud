package com.example.foodcloud.domain.bank.service.payment;

public interface PaymentService {
    String payment(Long userId, Long bankAccountId, int price);
}
