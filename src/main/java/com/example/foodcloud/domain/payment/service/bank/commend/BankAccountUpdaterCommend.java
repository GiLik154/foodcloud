package com.example.foodcloud.domain.payment.service.bank.commend;

import com.example.foodcloud.enums.PaymentCode;
import lombok.Getter;

@Getter
public class BankAccountUpdaterCommend {
    private final String name;
    private final String accountNumber;
    private final PaymentCode paymentCode;

    public BankAccountUpdaterCommend(String name, String accountNumber, PaymentCode paymentCode) {
        this.name = name;
        this.accountNumber = accountNumber;
        this.paymentCode = paymentCode;
    }
}
