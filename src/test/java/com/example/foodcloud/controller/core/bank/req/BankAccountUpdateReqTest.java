package com.example.foodcloud.controller.core.bank.req;

import com.example.foodcloud.domain.payment.service.bank.commend.BankAccountUpdaterCommend;
import com.example.foodcloud.enums.PaymentCode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountUpdateReqTest {
    @Test
    void Post_계좌_업데이트_Dto() {
        BankAccountUpdateReq bankAccountUpdateReq = new BankAccountUpdateReq("testName", "123456789", "004");

        assertEquals("testName", bankAccountUpdateReq.getName());
        assertEquals("123456789", bankAccountUpdateReq.getAccountNumber());
        assertEquals("004", bankAccountUpdateReq.getPaymentCode());
    }

    @Test
    void Post_계좌_업데이트_Dto_Convert() {
        BankAccountUpdateReq bankAccountUpdateReq = new BankAccountUpdateReq("testName", "123456789", "004");
        BankAccountUpdaterCommend bankAccountUpdaterCommend = bankAccountUpdateReq.convert();

        assertEquals("testName", bankAccountUpdaterCommend.getName());
        assertEquals("123456789", bankAccountUpdaterCommend.getAccountNumber());
        assertEquals(PaymentCode.KB, bankAccountUpdaterCommend.getPaymentCode());
    }
}