package com.example.foodcloud.entity;

import javax.persistence.*;

public class RecommendedFood {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long userID;
    @Column(nullable = false)
    private Long restaurantID;
    @Column(nullable = false)
    private Long foodMenuID;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
    @ManyToOne
    @JoinColumn(name = "food_menu_id")
    private FoodMenu foodMenu;

}
