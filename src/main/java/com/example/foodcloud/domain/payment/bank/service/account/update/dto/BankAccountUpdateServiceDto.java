package com.example.foodcloud.domain.payment.bank.service.account.update.dto;

import com.example.foodcloud.enums.PaymentCode;
import lombok.Getter;

@Getter
public class BankAccountUpdateServiceDto {
    private final String name;
    private final String accountNumber;
    private final PaymentCode paymentCode;

    public BankAccountUpdateServiceDto(String name, String accountNumber, PaymentCode paymentCode) {
        this.name = name;
        this.accountNumber = accountNumber;
        this.paymentCode = paymentCode;
    }
}
