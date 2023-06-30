package com.example.foodcloud.domain.payment.service.bank;

import com.example.foodcloud.domain.payment.domain.BankAccount;
import com.example.foodcloud.domain.payment.domain.BankAccountRepository;
import com.example.foodcloud.domain.payment.service.bank.commend.BankAccountRegisterCommend;
import com.example.foodcloud.domain.payment.service.bank.commend.BankAccountUpdaterCommend;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import com.example.foodcloud.domain.user.service.validate.ValidateUserPasswordService;
import com.example.foodcloud.exception.NotFoundBankAccountException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BankAccountService implements BankAccountRegister, BankAccountDeleter, BankAccountUpdater {
    private final UserRepository userRepository;
    private final BankAccountRepository bankAccountRepository;
    private final ValidateUserPasswordService validateUserPasswordService;

    @Override
    public void register(Long userId, BankAccountRegisterCommend bankAccountRegisterCommend) {
        User user = userRepository.getValidById(userId);

        BankAccount bankAccount = new BankAccount(user,
                bankAccountRegisterCommend.getName(),
                bankAccountRegisterCommend.getAccountNumber(),
                bankAccountRegisterCommend.getPaymentCode());

        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void delete(Long userId, Long bankAccountId, String password) {
        if (!bankAccountRepository.existsBankAccountByUserIdAndId(userId, bankAccountId)) {
            throw new NotFoundBankAccountException("Not exist bank account");
        }

        validateUserPasswordService.validate(userId, password);

        bankAccountRepository.deleteById(bankAccountId);
    }

    /**
     * userId와  bankAccountId로 BankAccount를 찾은 후
     * dto의 정보들로 업데이트 하는 메소드를 실행함.
     */
    @Override
    public void update(Long userId, Long bankAccountId, BankAccountUpdaterCommend commend) {
        bankAccountRepository.findByUserIdAndId(userId, bankAccountId)
                .ifPresentOrElse(bankAccount ->
                                bankAccount.update(commend.getName(), commend.getAccountNumber(), commend.getPaymentCode()),
                        () -> {
                            throw new NotFoundBankAccountException("Not exist bank account");
                        });
    }
}
