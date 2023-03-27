package com.example.foodcloud.domain.foodmenu.domain;

import com.example.foodcloud.domain.restaurant.domain.Restaurant;
import lombok.Getter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
public class FoodMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imagePath;
    private String foodMenuName;
    private String foodType;
    private String meatType;
    private String temperature;
    private String vegetables;
    private int price;
    private int orderCount;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    public FoodMenu() {
    }

    public FoodMenu(String foodMenuName, int price, String foodType, String temperature, String meatType, String vegetables, Restaurant restaurant) {
        this.foodMenuName = foodMenuName;
        this.price = price;
        this.foodType = foodType;
        this.temperature = temperature;
        this.meatType = meatType;
        this.vegetables = vegetables;
        this.restaurant = restaurant;
    }

    public void update(String foodMenuName, int price, String foodType, String temperature, String meatType, String vegetables) {
        this.foodMenuName = foodMenuName;
        this.price = price;
        this.foodType = foodType;
        this.temperature = temperature;
        this.meatType = meatType;
        this.vegetables = vegetables;
    }

    public void updateOrderMenu() {
        restaurant.updateOrderCount();

        this.orderCount++;
    }

    public void uploadImage(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        FoodMenu foodMenu = (FoodMenu) obj;
        return Objects.equals(id, foodMenu.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
