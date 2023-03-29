package com.example.foodcloud.enums.foodmenu;

public enum FoodTypes {
    RAMEN("라면"), UDON("우동"), JJAJANGMYEON("짜장면"), SPAGHETTI("스파게티"), JJAMPPONG("짬뽕"), SOBA("소바"), GUUKSU("국수"),
    BIBIMBAP("비빔밥"), DEOPBAP("덮밥"), BOKKEUMBAP("볶음밥"),
    SCONES("스콘"), BAGUETTE("바게트"), RED_BEAN_BREAD("팥빵"), SANDWICH("샌드위치"),
    AMERICANO("아메리카노"), LATTE("라떼"), ADE("에이드"), TEA("차"),
    CAKE("케이크"), ICE_CREAM("아이스크림"), PIE("파이"), PASTRY("페이스트리");


    private final String name;

    FoodTypes(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}