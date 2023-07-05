package com.example.foodcloud;

import com.example.foodcloud.domain.payment.domain.BankAccount;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.enums.PaymentCode;

public class BankAccountFixtures {
    private final User user;
    private String name = "testBankName";
    private String accountNumber = "testNumber";
    private final PaymentCode paymentCode;

    private BankAccountFixtures(User user, PaymentCode paymentCode) {
        this.user = user;
        this.paymentCode = paymentCode;
    }

    public static BankAccountFixtures fixtures(User user, PaymentCode paymentCode) {
        return new BankAccountFixtures(user, paymentCode);
    }

    public BankAccountFixtures name(String name) {
        this.name = name;
        return this;
    }

    public BankAccountFixtures accountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    public BankAccount build() {
        return new BankAccount(this.user, this.name, this.accountNumber, this.paymentCode);
    }
}
