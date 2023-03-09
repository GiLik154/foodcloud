package com.example.foodcloud.domain.bank.service.account.update;

import com.example.foodcloud.domain.bank.service.account.update.dto.BankAccountUpdateDto;

public interface BankAccountUpdateService {
    boolean update(Long userId, Long bankAccountId, BankAccountUpdateDto bankAccountUpdateDto);
}