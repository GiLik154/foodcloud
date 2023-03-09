package com.example.foodcloud.domain.bank.service.account.add.dto;

import lombok.Getter;

@Getter
public class BankAccountAddDto {
    private String name;
    private String accountNumber;
    private String bank;

    public BankAccountAddDto(String name, String accountNumber, String bank) {
        this.name = name;
        this.accountNumber = accountNumber;
        this.bank = bank;
    }
}
