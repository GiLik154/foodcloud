package com.example.foodcloud.domain.bank.service.account.delete;

public interface BankAccountDeleteService {
    boolean delete(Long userId, Long bankAccountId, String password);
}
