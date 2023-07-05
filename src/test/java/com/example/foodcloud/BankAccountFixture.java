package com.example.foodcloud;

import com.example.foodcloud.domain.payment.domain.BankAccount;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.enums.PaymentCode;

public class BankAccountFixture {
    private final User user;
    private String name = "testBankName";
    private String accountNumber = "testNumber";
    private final PaymentCode paymentCode;

    private BankAccountFixture(User user, PaymentCode paymentCode) {
        this.user = user;
        this.paymentCode = paymentCode;
    }

    public static BankAccountFixture fixture(User user, PaymentCode paymentCode) {
        return new BankAccountFixture(user, paymentCode);
    }

    public BankAccountFixture name(String name) {
        this.name = name;
        return this;
    }

    public BankAccountFixture accountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    public BankAccount build() {
        return new BankAccount(this.user, this.name, this.accountNumber, this.paymentCode);
    }
}
