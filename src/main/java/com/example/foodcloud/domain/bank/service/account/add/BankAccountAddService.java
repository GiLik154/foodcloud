package com.example.foodcloud.domain.bank.service.account.add;

import com.example.foodcloud.domain.bank.service.account.add.dto.BankAccountAddServiceDto;

public interface BankAccountAddService {
    void add(Long userId, BankAccountAddServiceDto bankAccountAddServiceDto);
}
