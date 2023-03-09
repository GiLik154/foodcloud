package com.example.foodcloud.domain.bank.service.account.delete;


import com.example.foodcloud.domain.bank.domain.BankAccountRepository;
import com.example.foodcloud.domain.user.service.validate.ValidateUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BankAccountDeleteServiceImpl implements BankAccountDeleteService {
    private final BankAccountRepository bankAccountRepository;
    private final ValidateUserService validateUserService;

    @Override
    public boolean delete(Long userId, Long bankAccountId, String password) {
        if (bankAccountRepository.existsBankAccountByUserIdAndId(userId, bankAccountId)) {

            validateUserService.validate(userId, password);

            bankAccountRepository.deleteById(bankAccountId);

            return true;
        }
        return false;
    }
}