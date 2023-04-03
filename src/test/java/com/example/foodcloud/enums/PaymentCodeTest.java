package com.example.foodcloud.enums;

import com.example.foodcloud.exception.NotFoundBankCodeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
        String point = PaymentCode.returnName("000");
        String kb = PaymentCode.returnName("004");
        String nh = PaymentCode.returnName("011");
        String shinHan = PaymentCode.returnName("088");

        assertEquals("포인트", point);
        assertEquals("국민", kb);
        assertEquals("농협", nh);
        assertEquals("신한", shinHan);
    }

    @Test
    void returnName_은행_찾지못함() {
        NotFoundBankCodeException e = assertThrows(NotFoundBankCodeException.class, () ->
                PaymentCode.returnName("111")
        );

        assertEquals("Not found BankCode", e.getMessage());
    }
}