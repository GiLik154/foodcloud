package com.example.foodcloud.domain.bank.service.account.add.dto;

import lombok.Getter;

@Getter
public class BankAccountAddServiceDto {
    private final String name;
    private final String accountNumber;
    private final String bank;

    public BankAccountAddServiceDto(String name, String accountNumber, String bank) {
        this.name = name;
        this.accountNumber = accountNumber;
        this.bank = bank;
    }
}
