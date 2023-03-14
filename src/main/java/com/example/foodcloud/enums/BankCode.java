package com.example.foodcloud.enums;

import com.example.foodcloud.exception.NotFoundBankCodeException;

public enum BankCode {
    KB("004", "국민"),
    MH("011", "농협"),
    SHIN_HAN("008", "신한");

    private final String code;
    private final String name;

    BankCode(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static String returnName(String code) {
        for (BankCode bankCode : BankCode.values()) {
            if (bankCode.code.equals(code)) {
                return bankCode.name;
            }
        }
        throw new NotFoundBankCodeException();
    }
}
