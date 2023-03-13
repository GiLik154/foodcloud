package com.example.foodcloud.controller.core.bank.dto;

import com.example.foodcloud.domain.bank.service.account.update.dto.BankAccountUpdateServiceDto;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class BankAccountUpdateControllerDto {
    @NotEmpty
    private final String name;
    @NotEmpty
    private final String accountNumber;
    @NotEmpty
    private final String bank;

    public BankAccountUpdateControllerDto(String name, String accountNumber, String bank) {
        this.name = name;
        this.accountNumber = accountNumber;
        this.bank = bank;
    }

    public BankAccountUpdateServiceDto convert() {
        return new BankAccountUpdateServiceDto(this.name, this.accountNumber, this.bank);
    }
}
