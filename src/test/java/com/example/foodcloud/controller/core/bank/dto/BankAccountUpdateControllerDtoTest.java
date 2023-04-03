package com.example.foodcloud.controller.core.bank.dto;

import com.example.foodcloud.domain.payment.bank.service.account.update.dto.BankAccountUpdateServiceDto;
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
        BankAccountUpdateServiceDto bankAccountUpdateServiceDto = bankAccountUpdateControllerDto.convert();

        assertEquals("testName", bankAccountUpdateServiceDto.getName());
        assertEquals("123456789", bankAccountUpdateServiceDto.getAccountNumber());
        assertEquals(PaymentCode.KB, bankAccountUpdateServiceDto.getPaymentCode());
    }
}