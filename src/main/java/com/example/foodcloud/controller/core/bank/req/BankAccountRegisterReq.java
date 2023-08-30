package com.example.foodcloud.controller.core.bank.req;

import com.example.foodcloud.domain.payment.service.bank.commend.BankAccountRegisterCommend;
import com.example.foodcloud.enums.PaymentCode;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class BankAccountRegisterReq {
    @NotEmpty
    private final String name;
    @NotEmpty
    private final String accountNumber;
    @NotEmpty
    private final String paymentCode;

    public BankAccountRegisterReq(String name, String accountNumber, String paymentCode) {
        this.name = name;
        this.accountNumber = accountNumber;
        this.paymentCode = paymentCode;
    }

    public BankAccountRegisterCommend convert() {
        return new BankAccountRegisterCommend(this.name, this.accountNumber, PaymentCode.valueOfCode(paymentCode));
    }
}
