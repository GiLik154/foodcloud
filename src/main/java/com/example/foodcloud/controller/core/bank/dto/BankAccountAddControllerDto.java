package com.example.foodcloud.controller.core.bank.dto;

import com.example.foodcloud.domain.bank.service.account.add.dto.BankAccountAddServiceDto;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class BankAccountAddControllerDto {
    @NotEmpty
    private final String name;
    @NotEmpty
    private final String accountNumber;
    @NotEmpty
    private final String bank;

    public BankAccountAddControllerDto(String name, String accountNumber, String bank) {
        this.name = name;
        this.accountNumber = accountNumber;
        this.bank = bank;
    }

    public BankAccountAddServiceDto convert() {
        return new BankAccountAddServiceDto(this.name, this.accountNumber, this.bank);
    }
}
