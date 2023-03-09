package com.example.foodcloud.domain.bank.service.account.add;

import com.example.foodcloud.domain.bank.service.account.add.dto.BankAccountAddDto;

public interface BankAccountAddService {
    void add(Long userId, BankAccountAddDto bankAccountAddDto);
}
