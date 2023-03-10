package com.example.foodcloud.domain.bank.service.account.add.dto;

import lombok.Getter;

@Getter
public class BankAccountAddServiceDto {
    private String name;
    private String accountNumber;
    private String bank;

    public BankAccountAddServiceDto(String name, String accountNumber, String bank) {
        this.name = name;
        this.accountNumber = accountNumber;
        this.bank = bank;
    }
}
