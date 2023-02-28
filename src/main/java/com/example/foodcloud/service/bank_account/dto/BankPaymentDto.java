package com.example.foodcloud.service.bank_account.dto;

import lombok.Getter;

@Getter
public class BankPaymentDto {
    private String accountNumber;
    private int bank;
    private Long userId;

    public BankPaymentDto(String accountNumber, int bank, Long userId) {
        this.accountNumber = accountNumber;
        this.bank = bank;
        this.userId = userId;
    }
}
