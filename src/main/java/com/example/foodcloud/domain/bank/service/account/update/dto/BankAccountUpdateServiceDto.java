package com.example.foodcloud.domain.bank.service.account.update.dto;

import lombok.Getter;

@Getter
public class BankAccountUpdateServiceDto {
    private final String name;
    private final String accountNumber;
    private final String bank;

    public BankAccountUpdateServiceDto(String name, String accountNumber, String bank) {
        this.name = name;
        this.accountNumber = accountNumber;
        this.bank = bank;
    }
}
