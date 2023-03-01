package com.example.foodcloud.domain.bank.service.account.update;

import com.example.foodcloud.domain.bank.domain.BankAccount;
import com.example.foodcloud.domain.bank.domain.BankAccountRepository;
import com.example.foodcloud.domain.bank.service.account.add.dto.BankAccountUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class BankAccountUpdateServiceImpl implements BankAccountUpdateService {
    private final BankAccountRepository bankAccountRepository;

    @Override
    public boolean update(Long userId, Long bankAccountId, BankAccountUpdateDto bankAccountUpdateDto) {
        Optional<BankAccount> bankAccountOpt = bankAccountRepository.findByUserIdAndId(userId, bankAccountId);

        if (bankAccountOpt.isPresent()) {

            BankAccount bankAccount = bankAccountOpt.get();

            bankAccount.update(bankAccountUpdateDto.getName(),
                    bankAccountUpdateDto.getAccountNumber(),
                    bankAccountUpdateDto.getBank());

            return true;
        }
        return false;
    }
}