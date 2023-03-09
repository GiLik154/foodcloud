package com.example.foodcloud.controller.bank.dto;

import com.example.foodcloud.domain.bank.service.account.add.dto.BankAccountAddServiceDto;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class BankAccountAddControllerDto {
    @NotEmpty
    private String name;
    @NotEmpty
    private String accountNumber;
    @NotEmpty
    private String bank;

    public BankAccountAddControllerDto(String name, String accountNumber, String bank) {
        this.name = name;
        this.accountNumber = accountNumber;
        this.bank = bank;
    }

    public BankAccountAddServiceDto convert() {
        return new BankAccountAddServiceDto(this.name, this.accountNumber, this.bank);
    }
}
