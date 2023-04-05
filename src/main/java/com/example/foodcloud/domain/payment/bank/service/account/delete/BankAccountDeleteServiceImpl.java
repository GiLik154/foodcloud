package com.example.foodcloud.domain.payment.bank.service.account.delete;


import com.example.foodcloud.domain.payment.bank.domain.BankAccountRepository;
import com.example.foodcloud.domain.user.service.validate.ValidateUserPasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * BankAccount를 삭제하는 서비스
 */
@Service
@Transactional
@RequiredArgsConstructor
public class BankAccountDeleteServiceImpl implements BankAccountDeleteService {
    private final BankAccountRepository bankAccountRepository;
    private final ValidateUserPasswordService validateUserPasswordService;

    /**
     * userId와 bankAccountId를 검증해준 후
     * 유저의 password도 검증해준다.
     * 이후 bankAccount의 PK를 통해 delete해준다.
     * @param password 유저가 입력한 패스워드
     */
    @Override
    public boolean delete(Long userId, Long bankAccountId, String password) {
        if (bankAccountRepository.existsBankAccountByUserIdAndId(userId, bankAccountId)) {

            validateUserPasswordService.validate(userId, password);

            bankAccountRepository.deleteById(bankAccountId);

            return true;
        }
        return false;
    }
}