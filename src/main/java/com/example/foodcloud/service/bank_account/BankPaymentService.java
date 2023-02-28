package com.example.foodcloud.service.bank_account;

import com.example.foodcloud.service.bank_account.dto.BankPaymentDto;

public interface BankPaymentService {
    String payment(BankPaymentDto bankPaymentDto);
}
