package com.example.foodcloud.domain.bank.service.account.delete;

import com.example.foodcloud.domain.bank.domain.BankAccount;
import com.example.foodcloud.domain.bank.domain.BankAccountRepository;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
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
class BankAccountDeleteServiceImplTest {
    private final BankAccountDeleteService bankAccountDeleteService;
    private final BankAccountRepository bankAccountRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public BankAccountDeleteServiceImplTest(BankAccountDeleteService bankAccountDeleteService, BankAccountRepository bankAccountRepository, UserRepository userRepository, PasswordEncoder bCryptPasswordEncoder) {
        this.bankAccountDeleteService = bankAccountDeleteService;
        this.bankAccountRepository = bankAccountRepository;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Test
    void 계좌_삭제_정상작동() {
        User user = new User("test", bCryptPasswordEncoder.encode("test"), "test");
        userRepository.save(user);

        BankAccount bankAccount = new BankAccount("test", "test", "001", user);
        bankAccountRepository.save(bankAccount);

        boolean isDelete = bankAccountDeleteService.delete(user.getId(), bankAccount.getId(), "test");

        assertTrue(isDelete);
        assertFalse(bankAccountRepository.existsById(bankAccount.getId()));
    }

    @Test
    void 계좌_삭제_유저고유번호_다름() { //
        User user = new User("test", bCryptPasswordEncoder.encode("test"), "test");
        userRepository.save(user);

        BankAccount bankAccount = new BankAccount("test", "test", "001", user);
        bankAccountRepository.save(bankAccount);

        boolean isDelete = bankAccountDeleteService.delete(user.getId() + 1L, bankAccount.getId(), "test");

        assertFalse(isDelete);
        assertTrue(bankAccountRepository.existsById(bankAccount.getId()));
    }

    @Test
    void 계좌_삭제_계좌고유번호_다름() {
        User user = new User("test", bCryptPasswordEncoder.encode("test"), "test");
        userRepository.save(user);

        BankAccount bankAccount = new BankAccount("test", "test", "001", user);
        bankAccountRepository.save(bankAccount);

        boolean isDelete = bankAccountDeleteService.delete(user.getId(), bankAccount.getId() + 1L, "test");

        assertFalse(isDelete);
        assertTrue(bankAccountRepository.existsById(bankAccount.getId()));
    }

    @Test
    void 계좌_삭제_비밀번호_다름() {
        User user = new User("test", bCryptPasswordEncoder.encode("test"), "test");
        userRepository.save(user);
        Long userId = user.getId();

        BankAccount bankAccount = new BankAccount("test", "test", "001", user);
        bankAccountRepository.save(bankAccount);
        Long bankAccountId = bankAccount.getId();

        BadCredentialsException e = assertThrows(BadCredentialsException.class, () ->
                bankAccountDeleteService.delete(userId, bankAccountId, "test123")
        );

        assertTrue(bankAccountRepository.existsById(bankAccount.getId()));
        assertEquals("Invalid password", e.getMessage());
    }
}