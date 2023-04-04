package com.example.foodcloud.domain.payment.bank.domain;

import com.example.foodcloud.domain.payment.Payment;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.enums.PaymentCode;
import lombok.Getter;

import javax.persistence.*;

/**
 * 계좌
 **/
@Entity
@Getter
public class BankAccount extends Payment {

    /**
     * 이름
     **/
    private String name;
    /**
     * 계좌번호
     **/
    private String accountNumber;

    public BankAccount() {
    }

    public BankAccount(String name, String accountNumber, User user, PaymentCode paymentCode) {
        init(user, paymentCode);
        this.name = name;
        this.accountNumber = accountNumber;
    }

    public void update(String name, String accountNumber, PaymentCode paymentCode) {
        updatePaymentCode(paymentCode);
        this.name = name;
        this.accountNumber = accountNumber;
    }
}
