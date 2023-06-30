package com.example.foodcloud.controller.core.bank.dto;

import com.example.foodcloud.domain.payment.bank.service.account.dto.BankAccountUpdaterCommend;
import com.example.foodcloud.enums.PaymentCode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountUpdateControllerDtoTest {
    @Test
    void Post_계좌_업데이트_Dto() {
        BankAccountUpdateControllerDto bankAccountUpdateControllerDto = new BankAccountUpdateControllerDto("testName", "123456789", "004");

        assertEquals("testName", bankAccountUpdateControllerDto.getName());
        assertEquals("123456789", bankAccountUpdateControllerDto.getAccountNumber());
        assertEquals("004", bankAccountUpdateControllerDto.getPaymentCode());
    }

    @Test
    void Post_계좌_업데이트_Dto_Convert() {
        BankAccountUpdateControllerDto bankAccountUpdateControllerDto = new BankAccountUpdateControllerDto("testName", "123456789", "004");
        BankAccountUpdaterCommend bankAccountUpdaterCommend = bankAccountUpdateControllerDto.convert();

        assertEquals("testName", bankAccountUpdaterCommend.getName());
        assertEquals("123456789", bankAccountUpdaterCommend.getAccountNumber());
        assertEquals(PaymentCode.KB, bankAccountUpdaterCommend.getPaymentCode());
    }
}