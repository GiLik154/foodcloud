package com.example.foodcloud.domain.payment.bank.service.account.delete;

import com.example.foodcloud.domain.payment.bank.domain.BankAccount;
import com.example.foodcloud.domain.payment.bank.domain.BankAccountRepository;
import com.example.foodcloud.domain.payment.bank.service.account.BankAccountDeleter;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import com.example.foodcloud.enums.PaymentCode;
import com.example.foodcloud.exception.NotFoundBankAccountException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BankAccountDeleterImplTest {
    private final BankAccountDeleter bankAccountDeleter;
    private final BankAccountRepository bankAccountRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public BankAccountDeleterImplTest(BankAccountDeleter bankAccountDeleter, BankAccountRepository bankAccountRepository, UserRepository userRepository, PasswordEncoder bCryptPasswordEncoder) {
        this.bankAccountDeleter = bankAccountDeleter;
        this.bankAccountRepository = bankAccountRepository;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Test
    void 계좌_삭제_정상작동() {
        User user = new User("test", bCryptPasswordEncoder.encode("test"), "test");
        userRepository.save(user);

        BankAccount bankAccount = new BankAccount(user, "testBankName", "testBankNumber", PaymentCode.KB);
        bankAccountRepository.save(bankAccount);

        bankAccountDeleter.delete(user.getId(), bankAccount.getId(), "test");

        assertFalse(bankAccountRepository.existsById(bankAccount.getId()));
    }

    @Test
    void 계좌_삭제_유저고유번호_다름() { //
        User user = new User("test", bCryptPasswordEncoder.encode("test"), "test");
        userRepository.save(user);
        Long userId = user.getId() + 1L;

        BankAccount bankAccount = new BankAccount(user, "testBankName", "testBankNumber", PaymentCode.KB);
        bankAccountRepository.save(bankAccount);
        Long bankAccountId = bankAccount.getId();

        assertThrows(NotFoundBankAccountException.class, () ->
                bankAccountDeleter.delete(userId, bankAccountId, "test"));

        assertTrue(bankAccountRepository.existsById(bankAccount.getId()));
    }

    @Test
    void 계좌_삭제_계좌고유번호_다름() {
        User user = new User("test", bCryptPasswordEncoder.encode("test"), "test");
        userRepository.save(user);
        Long userId = user.getId();

        BankAccount bankAccount = new BankAccount(user, "testBankName", "testBankNumber", PaymentCode.KB);
        bankAccountRepository.save(bankAccount);
        Long bankAccountId = bankAccount.getId() + 1L;

        assertThrows(NotFoundBankAccountException.class, () ->
                bankAccountDeleter.delete(userId, bankAccountId, "test"));

        assertTrue(bankAccountRepository.existsById(bankAccount.getId()));
    }

    @Test
    void 계좌_삭제_비밀번호_다름() {
        User user = new User("test", bCryptPasswordEncoder.encode("test"), "test");
        userRepository.save(user);
        Long userId = user.getId();

        BankAccount bankAccount = new BankAccount(user, "testBankName", "testBankNumber", PaymentCode.KB);
        bankAccountRepository.save(bankAccount);
        Long bankAccountId = bankAccount.getId();

        BadCredentialsException e = assertThrows(BadCredentialsException.class, () ->
                bankAccountDeleter.delete(userId, bankAccountId, "test123")
        );

        assertTrue(bankAccountRepository.existsById(bankAccount.getId()));
        assertEquals("Invalid password", e.getMessage());
    }
}