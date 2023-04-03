package com.example.foodcloud.domain.payment.bank.service.account.update;

import com.example.foodcloud.domain.payment.bank.domain.BankAccount;
import com.example.foodcloud.domain.payment.bank.domain.BankAccountRepository;
import com.example.foodcloud.domain.payment.bank.service.account.update.dto.BankAccountUpdateServiceDto;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import com.example.foodcloud.enums.PaymentCode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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

        BankAccount bankAccount = new BankAccount("testBankName", "testBankNumber", user, PaymentCode.KB);
        bankAccountRepository.save(bankAccount);
        Long bankAccountId = bankAccount.getId();

        BankAccountUpdateServiceDto bankAccountUpdateServiceDto = new BankAccountUpdateServiceDto("updateName", "updateNumber", PaymentCode.KB);
        boolean isUpdate = bankAccountUpdateService.update(userId, bankAccountId, bankAccountUpdateServiceDto);

        assertTrue(isUpdate);
        assertEquals("updateName", bankAccount.getName());
        assertEquals("updateNumber", bankAccount.getAccountNumber());
        assertEquals(PaymentCode.KB, bankAccount.getPaymentCode());
    }

    @Test
    void 계좌_업데이트_계정고유번호_다름() {
        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();

        BankAccount bankAccount = new BankAccount("testBankName", "testBankNumber", user, PaymentCode.NH);
        bankAccountRepository.save(bankAccount);
        Long bankAccountId = bankAccount.getId();

        BankAccountUpdateServiceDto bankAccountUpdateServiceDto = new BankAccountUpdateServiceDto("test123", "test123", PaymentCode.KB);
        boolean isUpdate = bankAccountUpdateService.update(userId + 1L, bankAccountId, bankAccountUpdateServiceDto);

        assertFalse(isUpdate);
        assertEquals("testBankName", bankAccount.getName());
        assertEquals("testBankNumber", bankAccount.getAccountNumber());
        assertEquals(PaymentCode.NH, bankAccount.getPaymentCode());
    }

    @Test
    void 계좌_업데이트_계좌고유번호_다름() {
        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();

        BankAccount bankAccount = new BankAccount("testBankName", "testBankNumber", user, PaymentCode.NH);
        bankAccountRepository.save(bankAccount);
        Long bankAccountId = bankAccount.getId();

        BankAccountUpdateServiceDto bankAccountUpdateServiceDto = new BankAccountUpdateServiceDto("test123", "test123", PaymentCode.KB);
        boolean isUpdate = bankAccountUpdateService.update(userId, bankAccountId + 1L, bankAccountUpdateServiceDto);

        assertFalse(isUpdate);
        assertEquals("testBankName", bankAccount.getName());
        assertEquals("testBankNumber", bankAccount.getAccountNumber());
        assertEquals(PaymentCode.NH, bankAccount.getPaymentCode());
    }
}