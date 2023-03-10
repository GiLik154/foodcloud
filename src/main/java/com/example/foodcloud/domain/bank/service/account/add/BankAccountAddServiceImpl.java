package com.example.foodcloud.domain.bank.service.account.add;

import com.example.foodcloud.domain.bank.domain.BankAccount;
import com.example.foodcloud.domain.bank.domain.BankAccountRepository;
import com.example.foodcloud.domain.bank.service.account.add.dto.BankAccountAddServiceDto;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BankAccountAddServiceImpl implements BankAccountAddService {
    private final BankAccountRepository bankAccountRepository;
    private final UserRepository userRepository;

    @Override
    public void add(Long userId, BankAccountAddServiceDto bankAccountAddServiceDto) {
        User user = userRepository.validateUser(userId);

        BankAccount bankAccount = new BankAccount(bankAccountAddServiceDto.getName(),
                bankAccountAddServiceDto.getAccountNumber(),
                bankAccountAddServiceDto.getBank(),
                user);

        bankAccountRepository.save(bankAccount);
    }
}