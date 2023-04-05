package com.example.foodcloud.domain.payment.bank.service.account.update;

import com.example.foodcloud.domain.payment.bank.service.account.update.dto.BankAccountUpdateServiceDto;

public interface BankAccountUpdateService {
    void update(Long userId, Long bankAccountId, BankAccountUpdateServiceDto bankAccountUpdateServiceDto);
}