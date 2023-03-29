package com.example.foodcloud.enums;

import com.example.foodcloud.exception.NotFoundBankCodeException;
import lombok.Getter;

@Getter
public enum BankCode {
    POINT("000", "포인트"),
    KB("004", "국민"),
    NH("011", "농협"),
    SHIN_HAN("088", "신한");

    private final String code;
    private final String name;

    BankCode(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String returnName(String code) {
        for (BankCode bankCode : BankCode.values()) {
            if (bankCode.code.equals(code)) {
                return bankCode.name;
            }
        }
        throw new NotFoundBankCodeException();
    }

    public static void validate(String code) {
        for (BankCode bankCode : BankCode.values()) {
            if (bankCode.code.equals(code)) {
                return;
            }
        }
        throw new NotFoundBankCodeException();
    }
}
