package com.example.foodcloud.service.bank_account.dto;

import lombok.Getter;

@Getter
public class BankAccountDto {
    private String name;
    private String accountNumber;
    private int bank;
    private Long userId;

    public BankAccountDto(String name, String accountNumber, int bank, Long userId) {
        this.name = name;
        this.accountNumber = accountNumber;
        this.bank = bank;
        this.userId = userId;
    }
}
