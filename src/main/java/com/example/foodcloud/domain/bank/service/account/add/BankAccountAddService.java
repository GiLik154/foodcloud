package com.example.foodcloud.domain.bank.service.account.add;

import com.example.foodcloud.domain.bank.service.account.add.dto.BankAccountUpdateDto;

public interface BankAccountAddService {
    void add(Long userId, BankAccountUpdateDto bankAccountAddDto);
}
