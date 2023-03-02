package com.example.foodcloud.domain.order.domain;

import com.example.foodcloud.domain.bank.domain.BankAccount;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.domain.restaurant.domain.Restaurant;
import com.example.foodcloud.domain.user.domain.User;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class OrderMenu {
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
    @ManyToOne
    @JoinColumn(name = "food_menu_id")
    private FoodMenu foodMenu;

    public OrderMenu(){}
    public OrderMenu(String location, String time, String result, User user, BankAccount bankAccount, Restaurant restaurant, FoodMenu foodMenu) {
        this.location = location;
        this.time = time;
        this.result = result;
        this.user = user;
        this.bankAccount = bankAccount;
        this.restaurant = restaurant;
        this.foodMenu = foodMenu;
    }
}
