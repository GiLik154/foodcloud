package com.example.foodcloud.domain.foodmenu.domain;

import com.example.foodcloud.domain.restaurant.domain.Restaurant;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class FoodMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String foodMenuName;
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

    public FoodMenu() {
    }

    public FoodMenu(String foodMenuName, int price, String foodType, String temperature, String meatType, String vegetables, String imagePath, Restaurant restaurant) {
        this.foodMenuName = foodMenuName;
        this.price = price;
        this.foodType = foodType;
        this.temperature = temperature;
        this.meatType = meatType;
        this.vegetables = vegetables;
        this.imagePath = imagePath;
        this.restaurant = restaurant;
    }
}
