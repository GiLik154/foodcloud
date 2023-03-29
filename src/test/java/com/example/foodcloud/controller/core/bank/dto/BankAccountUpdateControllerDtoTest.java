package com.example.foodcloud.controller.core.bank.dto;

import com.example.foodcloud.domain.payment.bank.service.account.update.dto.BankAccountUpdateServiceDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountUpdateControllerDtoTest {
    @Test
    void Post_계좌_업데이트_Dto() {
        BankAccountUpdateControllerDto bankAccountUpdateControllerDto = new BankAccountUpdateControllerDto("testName", "123456789", "Test Bank");

        assertEquals("testName", bankAccountUpdateControllerDto.getName());
        assertEquals("123456789", bankAccountUpdateControllerDto.getAccountNumber());
        assertEquals("Test Bank", bankAccountUpdateControllerDto.getBank());
    }

    @Test
    void Post_계좌_업데이트_Dto_Convert() {
        BankAccountUpdateControllerDto bankAccountUpdateControllerDto = new BankAccountUpdateControllerDto("testName", "123456789", "Test Bank");
        BankAccountUpdateServiceDto bankAccountUpdateServiceDto = bankAccountUpdateControllerDto.convert();

        assertEquals("testName", bankAccountUpdateServiceDto.getName());
        assertEquals("123456789", bankAccountUpdateServiceDto.getAccountNumber());
        assertEquals("Test Bank", bankAccountUpdateServiceDto.getBank());
    }
}