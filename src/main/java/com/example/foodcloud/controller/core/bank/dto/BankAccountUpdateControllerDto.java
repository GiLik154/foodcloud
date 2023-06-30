package com.example.foodcloud.controller.core.bank.dto;

import com.example.foodcloud.domain.payment.bank.service.account.dto.BankAccountUpdaterCommend;
import com.example.foodcloud.enums.PaymentCode;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class BankAccountUpdateControllerDto {
    @NotEmpty
    private final String name;
    @NotEmpty
    private final String accountNumber;
    @NotEmpty
    private final String paymentCode;

    public BankAccountUpdateControllerDto(String name, String accountNumber, String paymentCode) {
        this.name = name;
        this.accountNumber = accountNumber;
        this.paymentCode = paymentCode;
    }

    public BankAccountUpdaterCommend convert() {
        return new BankAccountUpdaterCommend(this.name, this.accountNumber, PaymentCode.valueOfCode(paymentCode));
    }
}

