package com.example.foodcloud.enums;

import com.example.foodcloud.enums.foodmenu.FoodTypes;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FoodTypeTest {

    @Test
    void getName() {
        String noodleName = FoodType.NOODLE.getName();
        String riceName = FoodType.RICE.getName();
        String breadName = FoodType.BREAD.getName();
        String drinkName = FoodType.DRINK.getName();
        String dessertName = FoodType.DESSERT.getName();

        assertEquals("면", noodleName);
        assertEquals("밥", riceName);
        assertEquals("빵", breadName);
        assertEquals("음료", drinkName);
        assertEquals("디저트", dessertName);
    }

    @Test
    void getKinds() {
        FoodTypes ramen = FoodType.NOODLE.getKinds().get(0);
        FoodTypes udon = FoodType.NOODLE.getKinds().get(1);
        FoodTypes jjajangmyeon = FoodType.NOODLE.getKinds().get(2);
        FoodTypes spaghetti = FoodType.NOODLE.getKinds().get(3);
        FoodTypes jjamppong = FoodType.NOODLE.getKinds().get(4);
        FoodTypes soba = FoodType.NOODLE.getKinds().get(5);
        FoodTypes guuksu = FoodType.NOODLE.getKinds().get(6);

        assertEquals(FoodTypes.RAMEN, ramen);
        assertEquals(FoodTypes.UDON, udon);
        assertEquals(FoodTypes.JJAJANGMYEON, jjajangmyeon);
        assertEquals(FoodTypes.SPAGHETTI, spaghetti);
        assertEquals(FoodTypes.JJAMPPONG, jjamppong);
        assertEquals(FoodTypes.SOBA, soba);
        assertEquals(FoodTypes.GUUKSU, guuksu);
    }
}