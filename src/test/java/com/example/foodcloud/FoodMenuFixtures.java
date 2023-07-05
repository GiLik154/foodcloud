package com.example.foodcloud;

import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.domain.restaurant.domain.Restaurant;
import com.example.foodcloud.enums.foodmenu.FoodTypes;
import com.example.foodcloud.enums.foodmenu.MeatTypes;
import com.example.foodcloud.enums.foodmenu.Temperature;
import com.example.foodcloud.enums.foodmenu.Vegetables;

public class FoodMenuFixtures {
    private String name = "testFoodMenuName";

    private int price = 5000;

    private Temperature temperature = Temperature.COLD;

    private FoodTypes foodTypes = FoodTypes.ADE;

    private MeatTypes meatType = MeatTypes.BEEF;

    private Vegetables vegetables = Vegetables.FEW;

    private Restaurant restaurant;


    private FoodMenuFixtures(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public static FoodMenuFixtures fixtures(Restaurant restaurant) {
        return new FoodMenuFixtures(restaurant);
    }

    public FoodMenuFixtures name(String name) {
        this.name = name;
        return this;
    }

    public FoodMenuFixtures price(int price) {
        this.price = price;
        return this;
    }

    public FoodMenuFixtures temperature(Temperature temperature) {
        this.temperature = temperature;
        return this;
    }

    public FoodMenuFixtures foodTypes(FoodTypes foodTypes) {
        this.foodTypes = foodTypes;
        return this;
    }

    public FoodMenuFixtures meatType(MeatTypes meatType) {
        this.meatType = meatType;
        return this;
    }

    public FoodMenuFixtures vegetables(Vegetables vegetables) {
        this.vegetables = vegetables;
        return this;
    }

    public FoodMenuFixtures restaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
        return this;
    }

    public FoodMenu build() {
        return new FoodMenu(this.name, this.price, this.temperature, this.foodTypes, this.meatType, this.vegetables, this.restaurant);
    }
}
