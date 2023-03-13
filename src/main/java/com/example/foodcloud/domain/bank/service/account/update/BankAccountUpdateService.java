package com.example.foodcloud.domain.bank.service.account.update;

import com.example.foodcloud.domain.bank.service.account.update.dto.BankAccountUpdateServiceDto;

public interface BankAccountUpdateService {
    boolean update(Long userId, Long bankAccountId, BankAccountUpdateServiceDto bankAccountUpdateServiceDto);
}