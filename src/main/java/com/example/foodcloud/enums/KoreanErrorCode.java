package com.example.foodcloud.enums;

import com.example.foodcloud.exception.NotFoundErrorCodeException;
import lombok.Getter;

@Getter
public enum KoreanErrorCode {
    BANK_NOT_FOUND("BANK_NOT_FOUND", "은행 정보를 확인해주세요."),
    FOOD_MENU_NOT_FOUND("FOOD_MENU_NOT_FOUND", "음식 메뉴 정보를 확인해주세요."),
    ORDER_MAIN_NOT_FOUND("ORDER_MAIN_NOT_FOUND", "오더 내용을 확인해주세요."),
    ORDER_MENU_NOT_FOUND("ORDER_MENU_NOT_FOUND", "주문 내역을 확인해주세요."),
    RESTAURANT_NOT_FOUND("RESTAURANT_NOT_FOUND", "식당 내용을 확인해 주세요."),
    NOT_ENOUGH_POINT("NOT_ENOUGH_POINT", "포인트가 부족합니다."),
    USER_NAME_DUPLICATE("USER_NAME_DUPLICATE", "아이디가 중복됩니다."),
    USER_INFO_NOT_FOUND("USER_INFO_NOT_FOUND", "유저 정보를 확인해주세요."),
    METHOD_ARGUMENT("METHOD_ARGUMENT", "입력값 오류가 발생하였습니다.");

    private final String name;
    private final String result;

    KoreanErrorCode(String name, String result) {
        this.name = name;
        this.result = result;
    }

    public static String findByResult(String name) {
        for (KoreanErrorCode koreanErrorCode : KoreanErrorCode.values()) {
            if (koreanErrorCode.name().equals(name)) {
                return koreanErrorCode.result;
            }
        }
        throw new NotFoundErrorCodeException();
    }
}
