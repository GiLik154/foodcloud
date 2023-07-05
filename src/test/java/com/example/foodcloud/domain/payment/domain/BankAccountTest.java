package com.example.foodcloud.domain.payment.domain;

import com.example.foodcloud.UserFixtures;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.enums.PaymentCode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {
    private final User user = UserFixtures.fixtures().build();

    @ParameterizedTest
    @ValueSource(strings = {"KB", "NH", "SHIN_HAN"})
    void 최상의_생성_테스트(String paymentCode) {
        PaymentCode code = PaymentCode.valueOf(paymentCode);
        BankAccount bankAccount = new BankAccount(user, "testName", "testNumber", code);

        assertEquals("testName", bankAccount.getName());
        assertEquals("testNumber", bankAccount.getAccountNumber());
        assertEquals(user, bankAccount.getUser());
        assertEquals(code, bankAccount.getPaymentCode());
    }

    @ParameterizedTest
    @ValueSource(strings = {"KB", "NH", "SHIN_HAN"})
    void 업데이트_테스트(String paymentCode) {
        PaymentCode code = PaymentCode.valueOf(paymentCode);
        BankAccount bankAccount = new BankAccount(user, "testName", "testNumber", null);

        bankAccount.update("newName", "newNumber", code);

        assertEquals("newName", bankAccount.getName());
        assertEquals("newNumber", bankAccount.getAccountNumber());
        assertEquals(code, bankAccount.getPaymentCode());
    }
}