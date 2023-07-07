package com.example.foodcloud.domain.payment.domain;

import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.enums.PaymentCode;
import lombok.Getter;

import javax.persistence.*;

/** 계좌 */
@Entity
@Getter
public class BankAccount extends Payment {
    /** 계좌의 등록 이름 */
    @Column(nullable = false)
    private String name;

    /** 계좌번호 */
    @Column(nullable = false)
    private String accountNumber;

    protected BankAccount() {}

    /** BankAccount의 기본 생성자 */
    public BankAccount(User user, String name, String accountNumber, PaymentCode paymentCode) {
        super(user, paymentCode);
        this.name = name;
        this.accountNumber = accountNumber;
    }

    /** BankAccount의 업데이트 메소드 */
    public void update(String name, String accountNumber, PaymentCode paymentCode) {
        updatePaymentCode(paymentCode);
        this.name = name;
        this.accountNumber = accountNumber;
    }
}