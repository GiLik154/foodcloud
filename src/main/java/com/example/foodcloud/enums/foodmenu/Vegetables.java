package com.example.foodcloud.enums.foodmenu;

public enum Vegetables {
    MANY("많이"),
    MIDDLE("중간"),
    FEW("조금"),
    NOTING("없음");

    private final String states;

    Vegetables(String states) {
        this.states = states;
    }

    public String getStates(){
        return this.states;
    }
}
