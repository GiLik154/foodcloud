package com.example.foodcloud.entity;

import javax.persistence.*;

@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userID;
    private Long orderID;
    private Long bankAccountID;
    private Long restaurantID;
    private String location;
    private String time;
    private String result;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "bank_account_id")
    private BankAccount bankAccount;
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
    @ManyToOne
    @JoinColumn(name = "food_menu_id")
    private FoodMenu foodMenu;
}
