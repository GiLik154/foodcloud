package com.example.foodcloud.domain.bank.service.payment;

public interface BankPaymentService {
    String payment(Long userId, Long bankAccountId, int price);
}
