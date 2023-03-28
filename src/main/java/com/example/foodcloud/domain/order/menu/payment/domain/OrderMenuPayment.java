package com.example.foodcloud.domain.order.menu.payment.domain;

import com.example.foodcloud.domain.order.menu.menu.domain.OrderMenu;
import com.example.foodcloud.domain.payment.Payment;
import com.example.foodcloud.domain.payment.bank.domain.BankAccount;
import com.example.foodcloud.domain.payment.point.domain.Point;
import com.example.foodcloud.enums.BankCode;
import com.example.foodcloud.enums.OrderResult;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class OrderMenuPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String payment;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_account_id")
    private BankAccount bankAccount;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "point_id")
    private Point point;

    public OrderMenuPayment() {
    }

    public OrderMenuPayment(String payment, BankAccount bankAccount) {
        this.payment = payment;
        this.bankAccount = bankAccount;
    }

    public OrderMenuPayment(String payment, Point point) {
        this.payment = payment;
        this.point = point;
    }
}
