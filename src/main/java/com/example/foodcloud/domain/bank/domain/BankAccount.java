package com.example.foodcloud.domain.bank.domain;

import com.example.foodcloud.domain.user.domain.User;
import lombok.Getter;

import javax.persistence.*;

/** 계좌 **/
@Entity
@Getter
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 이름 **/
    private String name;
    /** 계좌번호 **/
    private String accountNumber;
    /** 은행코드 **/
    private String bank;
    /** 유저 **/
    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;
    public BankAccount() {
    }

    public BankAccount(String name, String accountNumber, String bank, User user) {
        this.name = name;
        this.accountNumber = accountNumber;
        this.bank = bank;
        this.user = user;
    }

    public void update(String name, String accountNumber, String bank) {
        this.name = name;
        this.accountNumber = accountNumber;
        this.bank = bank;
    }
}
