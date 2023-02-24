package com.example.foodcloud.entity;

import javax.persistence.*;

@Entity
public class Hotspot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userID;
    private Long restaurantID;
    private Long foodMenuID;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
    @ManyToOne
    @JoinColumn(name = "food_menu_id")
    private FoodMenu foodMenu;
}
