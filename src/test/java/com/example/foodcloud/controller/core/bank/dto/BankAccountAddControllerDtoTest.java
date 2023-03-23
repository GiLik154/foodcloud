package com.example.foodcloud.controller.core.bank.dto;

import com.example.foodcloud.domain.payment.bank.service.account.add.dto.BankAccountAddServiceDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountAddControllerDtoTest {
    @Test
    void Post_계좌_추가_Dto() {
        BankAccountAddControllerDto bankAccountAddControllerDto = new BankAccountAddControllerDto("testName", "123456789", "Test Bank");

        assertEquals("testName", bankAccountAddControllerDto.getName());
        assertEquals("123456789", bankAccountAddControllerDto.getAccountNumber());
        assertEquals("Test Bank", bankAccountAddControllerDto.getBank());
    }

    @Test
    void Post_계좌_추가_Dto_Convert() {
        BankAccountAddControllerDto bankAccountAddControllerDto = new BankAccountAddControllerDto("testName", "123456789", "Test Bank");
        BankAccountAddServiceDto bankAccountAddServiceDto = bankAccountAddControllerDto.convert();

        assertEquals("testName", bankAccountAddServiceDto.getName());
        assertEquals("123456789", bankAccountAddServiceDto.getAccountNumber());
        assertEquals("Test Bank", bankAccountAddServiceDto.getBank());
    }
}