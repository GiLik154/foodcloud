package com.example.foodcloud.service.bank_account;

import com.example.foodcloud.entity.BankAccount;
import com.example.foodcloud.entity.BankAccountRepository;
import com.example.foodcloud.entity.User;
import com.example.foodcloud.entity.UserRepository;
import com.example.foodcloud.service.bank_account.dto.BankAccountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BankAccountAddServiceImpl implements BankAccountAddService {
    private final BankAccountRepository bankAccountRepository;
    private final UserRepository userRepository;

    @Override
    public void add(BankAccountDto bankAccountDto) {
        BankAccount bankAccount = new BankAccount(bankAccountDto.getName(),
                bankAccountDto.getAccountNumber(),
                bankAccountDto.getBank(),
                validateUser(bankAccountDto.getUserId())
        );
        bankAccountRepository.save(bankAccount);
    }

    private User validateUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new UsernameNotFoundException("Not found user name")
        );
    }
}
