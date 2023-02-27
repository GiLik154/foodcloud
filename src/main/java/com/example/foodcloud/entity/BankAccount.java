package com.example.foodcloud.entity;

import javax.persistence.*;

@Entity
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
}
