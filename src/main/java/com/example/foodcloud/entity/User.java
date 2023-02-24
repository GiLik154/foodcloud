package com.example.foodcloud.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 20)
    private String name;
    @Column(nullable = false, length = 20)
    private String password;
    @Column(nullable = false, length = 13)
    private String phone;
    @Column(nullable = true)
    private Long restaurantID;
    @OneToMany
    @JoinColumn(name = "restaurant_id")
    private List<Restaurant> restaurant;

    public User() {

    }

    public User(String name, String password, String phone) {
        this.name = name;
        this.password = password;
        this.phone = phone;
    }

    public User(String name, String password, String phone, Long restaurantID) {
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.restaurantID = restaurantID;
    }

    public boolean join() {

        return false;
    }

}

