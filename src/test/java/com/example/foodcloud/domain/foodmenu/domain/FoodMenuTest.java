package com.example.foodcloud.domain.foodmenu.domain;

import com.example.foodcloud.RestaurantFixture;
import com.example.foodcloud.UserFixture;
import com.example.foodcloud.domain.restaurant.domain.Restaurant;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.enums.foodmenu.FoodTypes;
import com.example.foodcloud.enums.foodmenu.MeatTypes;
import com.example.foodcloud.enums.foodmenu.Temperature;
import com.example.foodcloud.enums.foodmenu.Vegetables;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FoodMenuTest {

    @Test
    void 최상의_생성_테스트() {
        User user = UserFixture.fixture().build();
        Restaurant restaurant = RestaurantFixture.fixture(user).build();

        FoodMenu foodMenu = new FoodMenu("name", 5000, Temperature.COLD, FoodTypes.ADE, MeatTypes.BEEF, Vegetables.FEW, restaurant);

        assertEquals("name", foodMenu.getName());
        assertEquals(5000, foodMenu.getPrice());
        assertEquals(Temperature.COLD, foodMenu.getTemperature());
        assertEquals(FoodTypes.ADE, foodMenu.getFoodTypes());
        assertEquals(MeatTypes.BEEF, foodMenu.getMeatType());
        assertEquals(Vegetables.FEW, foodMenu.getVegetables());
        assertEquals(restaurant, foodMenu.getRestaurant());
    }

    @Test
    void 업데이트_메소드_정상작동() {
        User user = UserFixture.fixture().build();
        Restaurant restaurant = RestaurantFixture.fixture(user).build();

        FoodMenu foodMenu = new FoodMenu("name", 5000, Temperature.COLD, FoodTypes.ADE, MeatTypes.BEEF, Vegetables.FEW, restaurant);

        foodMenu.update("updateName", 10000, Temperature.HOT, FoodTypes.AMERICANO, MeatTypes.CHICKEN, Vegetables.MANY);

        assertEquals("updateName", foodMenu.getName());
        assertEquals(10000, foodMenu.getPrice());
        assertEquals(Temperature.HOT, foodMenu.getTemperature());
        assertEquals(FoodTypes.AMERICANO, foodMenu.getFoodTypes());
        assertEquals(MeatTypes.CHICKEN, foodMenu.getMeatType());
        assertEquals(Vegetables.MANY, foodMenu.getVegetables());
    }

    @Test
    void 주문횟수_증가_테스트() {
        User user = UserFixture.fixture().build();
        Restaurant restaurant = RestaurantFixture.fixture(user).build();

        FoodMenu foodMenu = new FoodMenu("name", 5000, Temperature.COLD, FoodTypes.ADE, MeatTypes.BEEF, Vegetables.FEW, restaurant);
        foodMenu.incrementOrderCount();

        assertEquals(1, foodMenu.getOrderCount());
    }

    @Test
    void 이미지_업데이트_테스트() {
        User user = UserFixture.fixture().build();
        Restaurant restaurant = RestaurantFixture.fixture(user).build();

        FoodMenu foodMenu = new FoodMenu("name", 5000, Temperature.COLD, FoodTypes.ADE, MeatTypes.BEEF, Vegetables.FEW, restaurant);
        foodMenu.uploadImage("testImagePath");

        assertEquals("testImagePath", foodMenu.getImagePath());
    }
}