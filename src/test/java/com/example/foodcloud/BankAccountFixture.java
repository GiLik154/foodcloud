package com.example.foodcloud;

import com.example.foodcloud.domain.payment.domain.BankAccount;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.enums.PaymentCode;

public class BankAccountFixture {
    private final User user;
    private String name = "testBankName";
    private String accountNumber = "testNumber";
    private PaymentCode paymentCode = PaymentCode.NH;

    private BankAccountFixture(User user) {
        this.user = user;
    }

    public static BankAccountFixture fixture(User user) {
        return new BankAccountFixture(user);
    }

    public BankAccountFixture name(String name) {
        this.name = name;
        return this;
    }

    public BankAccountFixture accountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    public BankAccountFixture paymentCode(PaymentCode paymentCode) {
        this.paymentCode = paymentCode;
        return this;
    }

    public BankAccount build() {
        return new BankAccount(this.user, this.name, this.accountNumber, this.paymentCode);
    }
}
