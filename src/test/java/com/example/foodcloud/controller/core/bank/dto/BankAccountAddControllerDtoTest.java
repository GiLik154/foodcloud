package com.example.foodcloud.controller.core.bank.dto;

import com.example.foodcloud.domain.payment.bank.service.account.add.dto.BankAccountAddServiceDto;
import com.example.foodcloud.enums.PaymentCode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountAddControllerDtoTest {
    @Test
    void Post_계좌_추가_Dto() {
        BankAccountAddControllerDto bankAccountAddControllerDto = new BankAccountAddControllerDto("testName", "123456789", "004");

        assertEquals("testName", bankAccountAddControllerDto.getName());
        assertEquals("123456789", bankAccountAddControllerDto.getAccountNumber());
        assertEquals("004", bankAccountAddControllerDto.getPaymentCode());
    }

    @Test
    void Post_계좌_추가_Dto_Convert() {
        BankAccountAddControllerDto bankAccountAddControllerDto = new BankAccountAddControllerDto("testName", "123456789", "004");
        BankAccountAddServiceDto bankAccountAddServiceDto = bankAccountAddControllerDto.convert();

        assertEquals("testName", bankAccountAddServiceDto.getName());
        assertEquals("123456789", bankAccountAddServiceDto.getAccountNumber());
        assertEquals(PaymentCode.KB, bankAccountAddServiceDto.getPaymentCode());
    }
}