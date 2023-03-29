package com.example.foodcloud.enums.foodmenu;

import lombok.Getter;

@Getter
public enum Temperature {
    HOT("뜨거움"),
    LUKEWARM("중간"),
    COLD("차가움");

    private final String states;

    Temperature(String states) {
        this.states = states;
    }
}
