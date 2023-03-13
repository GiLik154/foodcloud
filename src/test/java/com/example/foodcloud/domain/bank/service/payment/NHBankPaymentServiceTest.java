package com.example.foodcloud.domain.bank.service.payment;

import com.example.foodcloud.domain.bank.domain.BankAccount;
import com.example.foodcloud.domain.bank.domain.BankAccountRepository;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class NHBankPaymentServiceTest {
    private final Map<String, PaymentService> bankPaymentService;
    private final BankAccountRepository bankAccountRepository;
    private final UserRepository userRepository;

    @Autowired
    public NHBankPaymentServiceTest(Map<String, PaymentService> bankPaymentService, BankAccountRepository bankAccountRepository, UserRepository userRepository) {
        this.bankPaymentService = bankPaymentService;
        this.bankAccountRepository = bankAccountRepository;
        this.userRepository = userRepository;
    }

    private final String BANK_CODE = "011";

    @Test
    void NH_결제_정상작동() {
        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();

        BankAccount bankAccount = new BankAccount("test", "test", "011", user);
        bankAccountRepository.save(bankAccount);
        Long bankAccountId = bankAccount.getId();

        String result = bankPaymentService.get(BANK_CODE).payment(userId, bankAccountId, 5000);

        assertEquals("5000 price NH bank payment succeed", result);
    }

    @Test
    void NH_결제_계좌_고유번호_다름() {
        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();

        BankAccount bankAccount = new BankAccount("test", "test", "011", user);
        bankAccountRepository.save(bankAccount);
        Long bankAccountId = bankAccount.getId();

        String result = bankPaymentService.get(BANK_CODE).payment(userId, bankAccountId + 1L, 5000);

        assertEquals("NH bank payment fail", result);
    }

    @Test
    void NH_결제_아이디_고유번호_다름() {
        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();

        BankAccount bankAccount = new BankAccount("test", "test", "011", user);
        bankAccountRepository.save(bankAccount);
        Long bankAccountId = bankAccount.getId();

        String result = bankPaymentService.get(BANK_CODE).payment(userId +1L, bankAccountId, 5000);

        assertEquals("NH bank payment fail", result);
    }
}