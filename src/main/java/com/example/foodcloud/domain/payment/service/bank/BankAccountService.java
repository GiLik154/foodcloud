package com.example.foodcloud.domain.payment.service.bank;

import com.example.foodcloud.domain.payment.domain.BankAccount;
import com.example.foodcloud.domain.payment.domain.BankAccountRepository;
import com.example.foodcloud.domain.payment.service.bank.commend.BankAccountRegisterCommend;
import com.example.foodcloud.domain.payment.service.bank.commend.BankAccountUpdaterCommend;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import com.example.foodcloud.exception.NotFoundBankAccountException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BankAccountService implements BankAccountRegister, BankAccountUpdater, BankAccountDeleter {
    private final UserRepository userRepository;
    private final BankAccountRepository bankAccountRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    @Override
    public void register(Long userId, BankAccountRegisterCommend bankAccountRegisterCommend) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        BankAccount bankAccount = new BankAccount(user,
                bankAccountRegisterCommend.getName(),
                bankAccountRegisterCommend.getAccountNumber(),
                bankAccountRegisterCommend.getPaymentCode());

        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void update(Long userId, Long bankAccountId, BankAccountUpdaterCommend commend) {
        bankAccountRepository.findByUserIdAndId(userId, bankAccountId)
                .ifPresentOrElse(bankAccount -> bankAccount.update(commend.getName(), commend.getAccountNumber(), commend.getPaymentCode()),
                        () -> {
                            throw new NotFoundBankAccountException("Not exist bank account");
                        });
    }

    @Override
    public void delete(Long userId, Long bankAccountId, String password) {
        if (!bankAccountRepository.existsBankAccountByUserIdAndId(userId, bankAccountId)) {
            throw new NotFoundBankAccountException("Not exist bank account");
        }

        validUser(userId, password);

        bankAccountRepository.deleteById(bankAccountId);
    }

    private void validUser(Long userId, String password) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        validPassword(user, password);
    }

    private void validPassword(User user, String password){
        if (!user.isValidPassword(bCryptPasswordEncoder, password)) throw new BadCredentialsException("Invalid password");
    }
}
