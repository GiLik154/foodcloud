package com.example.foodcloud.enums;

import com.example.foodcloud.exception.NotFoundBankCodeException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum PaymentCode {
    POINT("000", "포인트"),
    KB("004", "국민"),
    NH("011", "농협"),
    SHIN_HAN("088", "신한");

    private final String code;
    private final String name;

    PaymentCode(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String returnName(PaymentCode code) {
        for (PaymentCode paymentCode : PaymentCode.values()) {
            if (paymentCode == code) {
                return paymentCode.name;
            }
        }
        throw new NotFoundBankCodeException();
    }

    public static PaymentCode valueOfCode(String code) {
        return Arrays.stream(values())
                .filter(value -> value.code.equals(code))
                .findAny()
                .orElse(null);
    }
}
