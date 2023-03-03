package com.example.foodcloud.domain.ordermain.domain;

import com.example.foodcloud.domain.bank.domain.BankAccount;
import com.example.foodcloud.domain.restaurant.domain.Restaurant;
import com.example.foodcloud.domain.user.domain.User;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class OrderMain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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


    public OrderMain(){}
    public OrderMain(String location, String time, User user, BankAccount bankAccount, Restaurant restaurant) {
        this.location = location;
        this.time = time;
        this.user = user;
        this.bankAccount = bankAccount;
        this.restaurant = restaurant;
        this.result = "Received";
    }

    public void updateResult(String result){
        this.result = result;
    }
}
