package com.example.foodcloud.controller.core.bank.req;

import com.example.foodcloud.domain.payment.service.bank.commend.BankAccountRegisterCommend;
import com.example.foodcloud.enums.PaymentCode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountRegisterReqTest {
    @Test
    void Post_계좌_추가_Dto() {
        BankAccountRegisterReq bankAccountRegisterReq = new BankAccountRegisterReq("testName", "123456789", "004");

        assertEquals("testName", bankAccountRegisterReq.getName());
        assertEquals("123456789", bankAccountRegisterReq.getAccountNumber());
        assertEquals("004", bankAccountRegisterReq.getPaymentCode());
    }

    @Test
    void Post_계좌_추가_Dto_Convert() {
        BankAccountRegisterReq bankAccountRegisterReq = new BankAccountRegisterReq("testName", "123456789", "004");
        BankAccountRegisterCommend bankAccountRegisterCommend = bankAccountRegisterReq.convert();

        assertEquals("testName", bankAccountRegisterCommend.getName());
        assertEquals("123456789", bankAccountRegisterCommend.getAccountNumber());
        assertEquals(PaymentCode.KB, bankAccountRegisterCommend.getPaymentCode());
    }
}