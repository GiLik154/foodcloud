package com.example.foodcloud.enums.foodmenu;

public enum MeatTypes {
    NONE("없음"),
    BEEF("소고기"),
    PORK("돼지고기"),
    CHICKEN("닭고기"),
    LAMB("양고기"),
    SEAFOOD("해산물"),
    OTHER("기타 등등");

    private final String name;

    MeatTypes(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}