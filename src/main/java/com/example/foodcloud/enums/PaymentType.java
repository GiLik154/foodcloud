package com.example.foodcloud.enums;


import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum PaymentType {
    POINT("PaymentUpdateForPoint", Arrays.asList(BankCode.POINT)),
    BANK("PaymentUpdateForBank", Arrays.asList(BankCode.KB, BankCode.SHIN_HAN, BankCode.MH)),
    EMPTY("noting", Collections.EMPTY_LIST);

    private final String code;
    private final List<BankCode> kinds;

    PaymentType(String code, List<BankCode> kinds) {
        this.code = code;
        this.kinds = kinds;
    }

    public String getCode() {
        return code;
    }

    public List<BankCode> getName() {
        return kinds;
    }

    public static String findByPaymentType(BankCode bankCode) {
        return Arrays.stream(PaymentType.values())
                .filter(paymentType -> paymentType.hasBankCode(bankCode))
                .findAny()
                .map(PaymentType::getCode)
                .orElse(EMPTY.getCode());
    }

    public boolean hasBankCode(BankCode bankCode) {
        return kinds.stream().anyMatch(pay -> pay == bankCode);
    }
}
