package com.example.foodcloud.domain.payment.domain;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    List<BankAccount> findByUserId(Long userId);

    boolean existsBankAccountByUserIdAndId(Long userId, Long bankAccountId);

    Optional<BankAccount> findByUserIdAndId(Long userId, Long bankAccountId);
}
