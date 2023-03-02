package com.example.foodcloud.domain.bank.domain;


import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.exception.NotFoundBankAccountException;
import com.example.foodcloud.exception.NotFoundRestaurantException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    List<BankAccount> findBankAccountByUserId(Long userId);

    boolean existsBankAccountByUserIdAndId(Long userId, Long bankAccountId);

    Optional<BankAccount> findByUserIdAndId(Long userId, Long bankAccountId);

    default BankAccount validateBankAccount(Long userId, Long bankAccountId) {
        Optional<BankAccount> bankAccountOptional = findByUserIdAndId(userId, bankAccountId);

        return bankAccountOptional.orElseThrow(() ->
                new NotFoundBankAccountException()
        );
    }
}
