package com.example.foodcloud.domain.payment.service.bank;

import com.example.foodcloud.UserFixture;
import com.example.foodcloud.domain.payment.domain.BankAccount;
import com.example.foodcloud.domain.payment.domain.BankAccountRepository;
import com.example.foodcloud.domain.payment.service.bank.commend.BankAccountUpdaterCommend;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import com.example.foodcloud.enums.PaymentCode;
import com.example.foodcloud.exception.NotFoundBankAccountException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BankAccountUpdaterTest {
    private final BankAccountUpdater bankAccountUpdater;
    private final BankAccountRepository bankAccountRepository;
    private final UserRepository userRepository;

    @Autowired
    public BankAccountUpdaterTest(BankAccountUpdater bankAccountUpdater, BankAccountRepository bankAccountRepository, UserRepository userRepository) {
        this.bankAccountUpdater = bankAccountUpdater;
        this.bankAccountRepository = bankAccountRepository;
        this.userRepository = userRepository;
    }

    @Test
    void 계좌_업데이트_정상작동() {
        User user = userRepository.save(UserFixture.fixture().build());
        userRepository.save(user);
        Long userId = user.getId();

        BankAccount bankAccount = new BankAccount(user, "testBankName", "testBankNumber", PaymentCode.KB);
        bankAccountRepository.save(bankAccount);
        Long bankAccountId = bankAccount.getId();

        BankAccountUpdaterCommend bankAccountUpdaterCommend = new BankAccountUpdaterCommend("updateName", "updateNumber", PaymentCode.KB);
        bankAccountUpdater.update(userId, bankAccountId, bankAccountUpdaterCommend);

        assertEquals("updateName", bankAccount.getName());
        assertEquals("updateNumber", bankAccount.getAccountNumber());
        assertEquals(PaymentCode.KB, bankAccount.getPaymentCode());
    }

    @Test
    void 유저의_고유번호가_다르면_익셉션이_발생해야함() {
        User user = userRepository.save(UserFixture.fixture().build());
        userRepository.save(user);
        Long userId = user.getId() + 1L;

        BankAccount bankAccount = new BankAccount(user, "testBankName", "testBankNumber", PaymentCode.NH);
        bankAccountRepository.save(bankAccount);
        Long bankAccountId = bankAccount.getId();

        BankAccountUpdaterCommend bankAccountUpdaterCommend = new BankAccountUpdaterCommend("test123", "test123", PaymentCode.KB);

        assertThrows(NotFoundBankAccountException.class, () ->
                bankAccountUpdater.update(userId, bankAccountId, bankAccountUpdaterCommend));

        assertEquals("testBankName", bankAccount.getName());
        assertEquals("testBankNumber", bankAccount.getAccountNumber());
        assertEquals(PaymentCode.NH, bankAccount.getPaymentCode());
    }

    @Test
    void 계좌의_고유번호가_다르면_익셉션이_발생해야함() {
        User user = userRepository.save(UserFixture.fixture().build());
        userRepository.save(user);
        Long userId = user.getId();

        BankAccount bankAccount = new BankAccount(user, "testBankName", "testBankNumber", PaymentCode.NH);
        bankAccountRepository.save(bankAccount);
        Long bankAccountId = bankAccount.getId() + 1L;

        BankAccountUpdaterCommend bankAccountUpdaterCommend = new BankAccountUpdaterCommend("test123", "test123", PaymentCode.KB);

        assertThrows(NotFoundBankAccountException.class, () ->
                bankAccountUpdater.update(userId, bankAccountId, bankAccountUpdaterCommend));

        assertEquals("testBankName", bankAccount.getName());
        assertEquals("testBankNumber", bankAccount.getAccountNumber());
        assertEquals(PaymentCode.NH, bankAccount.getPaymentCode());
    }
}