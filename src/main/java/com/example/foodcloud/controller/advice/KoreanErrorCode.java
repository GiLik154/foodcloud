package com.example.foodcloud.controller.advice;

public enum KoreanErrorCode {
    NOT_FOUND_BANK("은행 정보를 확인해주세요."),
    NOT_FOUND_FOOD_MENU("음식 메뉴 정보를 확인해주세요."),
    NOT_FOUND_ORDER_MAIN("오더 내용을 확인해주세요."),
    NOT_FOUND_RESTAURANT("식당 내용을 확인해 주세요."),
    OUT_OF_BOUNDS("입력값 범위 초과입니다."),
    USER_NAME_DUPLICATE("아이디가 중복됩니다."),
    USER_INFO_NOT_FOUND("유저 정보를 확인해주세요."),
    METHOD_ARGUMENT("입력값 오류가 발생하였습니다.");

    private final String result;

    KoreanErrorCode(String name) {
        this.result = name;
    }

    public String getResult() {
        return result;
    }
}