package com.example.foodcloud.domain.payment.bank.service.account;

import com.example.foodcloud.domain.payment.domain.BankAccount;
import com.example.foodcloud.domain.payment.domain.BankAccountRepository;
import com.example.foodcloud.domain.payment.service.bank.BankAccountRegister;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import com.example.foodcloud.domain.payment.service.bank.commend.BankAccountRegisterCommend;
import com.example.foodcloud.enums.PaymentCode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BankAccountRegisterTest {
    private final BankAccountRegister bankAccountRegister;
    private final BankAccountRepository bankAccountRepository;
    private final UserRepository userRepository;

    @Autowired
    BankAccountRegisterTest(BankAccountRegister bankAccountRegister, BankAccountRepository bankAccountRepository, UserRepository userRepository) {
        this.bankAccountRegister = bankAccountRegister;
        this.bankAccountRepository = bankAccountRepository;
        this.userRepository = userRepository;
    }

    @Test
    void 계좌_추가_정상작동() {
        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();

        BankAccountRegisterCommend bankAccountRegisterCommend = new BankAccountRegisterCommend("testName", "testNumber", PaymentCode.KB);
        bankAccountRegister.register(userId, bankAccountRegisterCommend);

        BankAccount bankAccount = bankAccountRepository.findByUserId(userId).get(0);


        assertNotNull(bankAccount.getId());
        assertEquals(user, bankAccount.getUser());
        assertEquals("testName", bankAccount.getName());
        assertEquals("testNumber", bankAccount.getAccountNumber());
        assertEquals(PaymentCode.KB, bankAccount.getPaymentCode());
    }

    @Test
    void 계좌_추가_유저_없음() {
        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();

        BankAccountRegisterCommend bankAccountRegisterCommend = new BankAccountRegisterCommend("testName", "testNumber", PaymentCode.KB);

        assertThrows(UsernameNotFoundException.class, () -> bankAccountRegister.register(userId + 1L, bankAccountRegisterCommend));
    }
}