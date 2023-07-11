package com.example.foodcloud.domain.payment.service.bank;

import com.example.foodcloud.BankAccountFixture;
import com.example.foodcloud.UserFixture;
import com.example.foodcloud.domain.payment.domain.BankAccount;
import com.example.foodcloud.domain.payment.domain.BankAccountRepository;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
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
class BankAccountDeleterTest {
    private final BankAccountDeleter bankAccountDeleter;
    private final BankAccountRepository bankAccountRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public BankAccountDeleterTest(BankAccountDeleter bankAccountDeleter, BankAccountRepository bankAccountRepository, UserRepository userRepository, PasswordEncoder bCryptPasswordEncoder) {
        this.bankAccountDeleter = bankAccountDeleter;
        this.bankAccountRepository = bankAccountRepository;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Test
    void 계좌_삭제_정상작동() {
        User user = userRepository.save(UserFixture.fixture().encoding(bCryptPasswordEncoder, "testPassword").build());
        BankAccount bankAccount = bankAccountRepository.save(BankAccountFixture.fixture(user).build());

        Long userId = user.getId();
        Long bankAccountId = bankAccount.getId();

        bankAccountDeleter.delete(userId, bankAccountId, "testPassword");

        assertFalse(bankAccountRepository.existsById(bankAccountId));
    }

    @Test
    void 계좌_삭제_유저고유번호_다름() { //
        User user = userRepository.save(UserFixture.fixture().encoding(bCryptPasswordEncoder, "testPassword").build());
        BankAccount bankAccount = bankAccountRepository.save(BankAccountFixture.fixture(user).build());

        Long userId = user.getId();
        Long bankAccountId = bankAccount.getId();

        assertThrows(NotFoundBankAccountException.class, () -> bankAccountDeleter.delete(userId + 1L, bankAccountId, "testPassword"));

        assertTrue(bankAccountRepository.existsById(bankAccount.getId()));
    }

    @Test
    void 계좌_삭제_계좌고유번호_다름() {
        User user = userRepository.save(UserFixture.fixture().encoding(bCryptPasswordEncoder, "testPassword").build());
        BankAccount bankAccount = bankAccountRepository.save(BankAccountFixture.fixture(user).build());

        Long userId = user.getId();
        Long bankAccountId = bankAccount.getId();

        assertThrows(NotFoundBankAccountException.class, () -> bankAccountDeleter.delete(userId, bankAccountId + 1L, "testPassword"));

        assertTrue(bankAccountRepository.existsById(bankAccount.getId()));
    }

    @Test
    void 계좌_삭제_비밀번호_다름() {
        User user = userRepository.save(UserFixture.fixture().encoding(bCryptPasswordEncoder, "testPassword").build());
        BankAccount bankAccount = bankAccountRepository.save(BankAccountFixture.fixture(user).build());

        Long userId = user.getId();
        Long bankAccountId = bankAccount.getId();

        assertThrows(BadCredentialsException.class, () -> bankAccountDeleter.delete(userId, bankAccountId, "wrongPassword"));

        assertTrue(bankAccountRepository.existsById(bankAccount.getId()));
    }
}