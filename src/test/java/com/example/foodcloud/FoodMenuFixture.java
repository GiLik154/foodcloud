package com.example.foodcloud;

import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.domain.restaurant.domain.Restaurant;
import com.example.foodcloud.enums.foodmenu.FoodTypes;
import com.example.foodcloud.enums.foodmenu.MeatTypes;
import com.example.foodcloud.enums.foodmenu.Temperature;
import com.example.foodcloud.enums.foodmenu.Vegetables;

public class FoodMenuFixture {
    private String name = "testFoodMenuName";

    private int price = 5000;

    private Temperature temperature = Temperature.COLD;

    private FoodTypes foodTypes = FoodTypes.ADE;

    private MeatTypes meatType = MeatTypes.BEEF;

    private Vegetables vegetables = Vegetables.FEW;

    private Restaurant restaurant;


    private FoodMenuFixture(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public static FoodMenuFixture fixture(Restaurant restaurant) {
        return new FoodMenuFixture(restaurant);
    }

    public FoodMenuFixture name(String name) {
        this.name = name;
        return this;
    }

    public FoodMenuFixture price(int price) {
        this.price = price;
        return this;
    }

    public FoodMenuFixture temperature(Temperature temperature) {
        this.temperature = temperature;
        return this;
    }

    public FoodMenuFixture foodTypes(FoodTypes foodTypes) {
        this.foodTypes = foodTypes;
        return this;
    }

    public FoodMenuFixture meatType(MeatTypes meatType) {
        this.meatType = meatType;
        return this;
    }

    public FoodMenuFixture vegetables(Vegetables vegetables) {
        this.vegetables = vegetables;
        return this;
    }

    public FoodMenuFixture restaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
        return this;
    }

    public FoodMenu build() {
        return new FoodMenu(this.name, this.price, this.temperature, this.foodTypes, this.meatType, this.vegetables, this.restaurant);
    }
}
