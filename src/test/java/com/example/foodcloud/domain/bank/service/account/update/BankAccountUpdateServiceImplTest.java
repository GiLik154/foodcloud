package com.example.foodcloud.domain.bank.service.account.update;

import com.example.foodcloud.domain.bank.domain.BankAccount;
import com.example.foodcloud.domain.bank.domain.BankAccountRepository;
import com.example.foodcloud.domain.bank.service.account.update.BankAccountUpdateService;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import com.example.foodcloud.domain.bank.service.account.add.dto.BankAccountUpdateDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BankAccountUpdateServiceImplTest {
    private final BankAccountUpdateService bankAccountUpdateService;
    private final BankAccountRepository bankAccountRepository;
    private final UserRepository userRepository;

    @Autowired
    public BankAccountUpdateServiceImplTest(BankAccountUpdateService bankAccountUpdateService, BankAccountRepository bankAccountRepository, UserRepository userRepository) {
        this.bankAccountUpdateService = bankAccountUpdateService;
        this.bankAccountRepository = bankAccountRepository;
        this.userRepository = userRepository;
    }

    @Test
    void 계좌_업데이트_정상작동() {
        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();

        BankAccount bankAccount = new BankAccount("test", "test", "001", user);
        bankAccountRepository.save(bankAccount);
        Long bankAccountId = bankAccount.getId();

        BankAccountUpdateDto bankAccountUpdateDto = new BankAccountUpdateDto("test123", "test123", "011");
        boolean isUpdate = bankAccountUpdateService.update(userId, bankAccountId, bankAccountUpdateDto);

        assertTrue(isUpdate);
        assertEquals("test123", bankAccount.getName());
        assertEquals("test123", bankAccount.getAccountNumber());
        assertEquals("011", bankAccount.getBank());
    }

    @Test
    void 계좌_업데이트_계정고유번호_다름() {
        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();

        BankAccount bankAccount = new BankAccount("test", "test", "001", user);
        bankAccountRepository.save(bankAccount);
        Long bankAccountId = bankAccount.getId();

        BankAccountUpdateDto bankAccountUpdateDto = new BankAccountUpdateDto("test123", "test123", "011");
        boolean isUpdate = bankAccountUpdateService.update(userId + 1L, bankAccountId, bankAccountUpdateDto);

        assertFalse(isUpdate);
        assertEquals("test", bankAccount.getName());
        assertEquals("test", bankAccount.getAccountNumber());
        assertEquals("001", bankAccount.getBank());
    }

    @Test
    void 계좌_업데이트_계좌고유번호_다름() {
        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();

        BankAccount bankAccount = new BankAccount("test", "test", "001", user);
        bankAccountRepository.save(bankAccount);
        Long bankAccountId = bankAccount.getId();

        BankAccountUpdateDto bankAccountUpdateDto = new BankAccountUpdateDto("test123", "test123", "011");
        boolean isUpdate = bankAccountUpdateService.update(userId, bankAccountId + 1L, bankAccountUpdateDto);

        assertFalse(isUpdate);
        assertEquals("test", bankAccount.getName());
        assertEquals("test", bankAccount.getAccountNumber());
        assertEquals("001", bankAccount.getBank());
    }
}