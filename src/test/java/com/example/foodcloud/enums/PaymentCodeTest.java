package com.example.foodcloud.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PaymentCodeTest {

    @Test
    void getName_정상작동() {
        assertEquals("포인트", PaymentCode.POINT.getName());
        assertEquals("국민", PaymentCode.KB.getName());
        assertEquals("농협", PaymentCode.NH.getName());
        assertEquals("신한", PaymentCode.SHIN_HAN.getName());
    }
    
    @Test
    void returnName_정상작동() {
        String point = PaymentCode.returnName(PaymentCode.POINT);
        String kb = PaymentCode.returnName(PaymentCode.KB);
        String nh = PaymentCode.returnName(PaymentCode.NH);
        String shinHan = PaymentCode.returnName(PaymentCode.SHIN_HAN);

        assertEquals("포인트", point);
        assertEquals("국민", kb);
        assertEquals("농협", nh);
        assertEquals("신한", shinHan);
    }
}