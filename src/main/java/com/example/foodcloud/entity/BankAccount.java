package com.example.foodcloud.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String accountNumber;
    private int bank;
    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;
    public BankAccount() {
    }

    public BankAccount(String name, String accountNumber, int bank, User user) {
        this.name = name;
        this.accountNumber = accountNumber;
        this.bank = bank;
        this.user = user;
    }

}
