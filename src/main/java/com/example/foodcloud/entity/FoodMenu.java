package com.example.foodcloud.entity;

import javax.persistence.*;

@Entity
public class FoodMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String foodMenu;
    private int price;
    private String foodType;
    private String temperature;
    private String meatType;
    private String vegetables;
    private int orderCount;
    private String imagePath;
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
}
