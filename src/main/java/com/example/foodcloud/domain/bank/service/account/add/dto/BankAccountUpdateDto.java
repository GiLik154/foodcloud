package com.example.foodcloud.domain.bank.service.account.add.dto;

import lombok.Getter;

@Getter
public class BankAccountUpdateDto {
    private String name;
    private String accountNumber;
    private String bank;

    public BankAccountUpdateDto(String name, String accountNumber, String bank) {
        this.name = name;
        this.accountNumber = accountNumber;
        this.bank = bank;
    }
}
