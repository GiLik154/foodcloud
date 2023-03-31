package com.example.foodcloud.domain.foodmenu.domain;

import com.example.foodcloud.domain.restaurant.domain.Restaurant;
import com.example.foodcloud.enums.foodmenu.FoodTypes;
import com.example.foodcloud.enums.foodmenu.MeatTypes;
import com.example.foodcloud.enums.foodmenu.Temperature;
import com.example.foodcloud.enums.foodmenu.Vegetables;
import lombok.Getter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
public class FoodMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int price;
    private int orderCount;
    private Temperature temperature;
    private FoodTypes foodTypes;
    private MeatTypes meatType;
    private Vegetables vegetables;
    private String imagePath;
    @ManyToOne(fetch = FetchType.LAZY)
    private Restaurant restaurant;

    public FoodMenu() {
    }

    public FoodMenu(String name, int price, Temperature temperature, FoodTypes foodTypes, MeatTypes meatType, Vegetables vegetables, Restaurant restaurant) {
        this.name = name;
        this.price = price;
        this.temperature = temperature;
        this.foodTypes = foodTypes;
        this.meatType = meatType;
        this.vegetables = vegetables;
        this.restaurant = restaurant;
    }

    public void update(String foodMenuName, int price, Temperature temperature, FoodTypes foodTypes, MeatTypes meatType, Vegetables vegetables) {
        this.name = foodMenuName;
        this.price = price;
        this.temperature = temperature;
        this.foodTypes = foodTypes;
        this.meatType = meatType;
        this.vegetables = vegetables;
    }

    public void updateOrderMenu() {
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