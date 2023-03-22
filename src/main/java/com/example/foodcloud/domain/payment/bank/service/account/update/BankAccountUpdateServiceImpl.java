package com.example.foodcloud.domain.payment.bank.service.account.update;

import com.example.foodcloud.domain.payment.bank.domain.BankAccount;
import com.example.foodcloud.domain.payment.bank.domain.BankAccountRepository;
import com.example.foodcloud.domain.payment.bank.service.account.update.dto.BankAccountUpdateServiceDto;
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
    public boolean update(Long userId, Long bankAccountId, BankAccountUpdateServiceDto bankAccountUpdateServiceDto) {
        System.out.println(userId + "우저아이디" + bankAccountId + "이게 잘 나오냥>");
        Optional<BankAccount> bankAccountOpt = bankAccountRepository.findByUserIdAndId(userId, bankAccountId);

        if (bankAccountOpt.isPresent()) {

            BankAccount bankAccount = bankAccountOpt.get();

            bankAccount.update(bankAccountUpdateServiceDto.getName(),
                    bankAccountUpdateServiceDto.getAccountNumber(),
                    bankAccountUpdateServiceDto.getBank());

            return true;
        }
        return false;
    }
}