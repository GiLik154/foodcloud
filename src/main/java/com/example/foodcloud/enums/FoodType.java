package com.example.foodcloud.enums;

import com.example.foodcloud.enums.foodmenu.FoodTypes;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
@Getter
public enum FoodType {
    NOODLE("면", Arrays.asList(FoodTypes.RAMEN, FoodTypes.UDON, FoodTypes.JJAJANGMYEON, FoodTypes.SPAGHETTI, FoodTypes.JJAMPPONG, FoodTypes.SOBA, FoodTypes.GUUKSU)),
    RICE("밥", Arrays.asList(FoodTypes.BIBIMBAP, FoodTypes.DEOPBAP, FoodTypes.BOKKEUMBAP)),
    BREAD("빵", Arrays.asList(FoodTypes.SCONES, FoodTypes.BAGUETTE, FoodTypes.RED_BEAN_BREAD, FoodTypes.SANDWICH)),
    DRINK("음료", Arrays.asList(FoodTypes.AMERICANO, FoodTypes.LATTE, FoodTypes.ADE, FoodTypes.TEA)),
    DESSERT("디저트", Arrays.asList(FoodTypes.CAKE, FoodTypes.ICE_CREAM, FoodTypes.PIE, FoodTypes.PASTRY));

    private String name;
    private List<FoodTypes> kinds;

    FoodType(String name, List<FoodTypes> foodTypes) {
        this.name = name;
        this.kinds = foodTypes;
    }
}