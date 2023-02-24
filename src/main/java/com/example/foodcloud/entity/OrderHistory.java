package com.example.foodcloud.entity;

import javax.persistence.*;

@Entity
public class OrderHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userID;
    private Long orderID;
    private Long bankAccountID;
    private String location;
    private String menu;
    private int count;
    private String time;
    private String result;
    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne()
    @JoinColumn(name = "order_id")
    private Order order;
    @ManyToOne()
    @JoinColumn(name = "bank_account_id")
    private BankAccount bankAccount;
}
