package com.example.foodcloud.enums;

import com.example.foodcloud.exception.NotFoundBankCodeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankCodeTest {

    @Test
    void getName_정상작동() {
        assertEquals("포인트", BankCode.POINT.getName());
        assertEquals("국민", BankCode.KB.getName());
        assertEquals("농협", BankCode.NH.getName());
        assertEquals("신한", BankCode.SHIN_HAN.getName());
    }
    
    @Test
    void returnName_정상작동() {
        String point = BankCode.returnName("000");
        String kb = BankCode.returnName("004");
        String nh = BankCode.returnName("011");
        String shinHan = BankCode.returnName("088");

        assertEquals("포인트", point);
        assertEquals("국민", kb);
        assertEquals("농협", nh);
        assertEquals("신한", shinHan);
    }

    @Test
    void returnName_은행_찾지못함() {
        NotFoundBankCodeException e = assertThrows(NotFoundBankCodeException.class, () ->
                BankCode.returnName("111")
        );

        assertEquals("Not found BankCode", e.getMessage());
    }
}