package com.example.foodcloud.domain.foodmenu.domain;

import com.example.foodcloud.domain.order.menu.domain.OrderMenu;
import com.example.foodcloud.domain.restaurant.domain.Restaurant;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
