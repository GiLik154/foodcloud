package com.example.foodcloud.controller.core.bank;

import com.example.foodcloud.controller.advice.NotFoundExceptionAdvice;
import com.example.foodcloud.controller.advice.UserExceptionAdvice;
import com.example.foodcloud.controller.interceptor.LoginInterceptor;
import com.example.foodcloud.domain.payment.bank.domain.BankAccount;
import com.example.foodcloud.domain.payment.bank.domain.BankAccountRepository;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import com.example.foodcloud.enums.PaymentCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BankUpdatePostControllerTest {
    private final BankUpdateController bankUpdateController;
    private final UserExceptionAdvice userExceptionAdvice;
    private final LoginInterceptor loginInterceptor;
    private final UserRepository userRepository;
    private final BankAccountRepository bankAccountRepository;

    private final NotFoundExceptionAdvice notFoundExceptionAdvice;

    private MockMvc mockMvc;

    @Autowired
    public BankUpdatePostControllerTest(BankUpdateController bankUpdateController,
                                        UserExceptionAdvice userExceptionAdvice,
                                        LoginInterceptor loginInterceptor,
                                        UserRepository userRepository,
                                        BankAccountRepository bankAccountRepository, NotFoundExceptionAdvice notFoundExceptionAdvice) {

        this.bankUpdateController = bankUpdateController;
        this.userExceptionAdvice = userExceptionAdvice;
        this.loginInterceptor = loginInterceptor;
        this.userRepository = userRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.notFoundExceptionAdvice = notFoundExceptionAdvice;
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(bankUpdateController)
                .setControllerAdvice(userExceptionAdvice, notFoundExceptionAdvice)
                .addInterceptors(loginInterceptor)
                .build();
    }

    @Test
    void Post_계좌_업데이트_정상작동() throws Exception {
        User user = new User("testName", "testPassword", "testPhone");
        userRepository.save(user);

        BankAccount bankAccount = new BankAccount(user, "testBankName", "testBankNumber", PaymentCode.KB);
        bankAccountRepository.save(bankAccount);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = post("/bank/update")
                .param("bankAccountId", String.valueOf(bankAccount.getId()))
                .param("name", "updateBankName")
                .param("accountNumber", "updateBankNumber")
                .param("paymentCode", "088")
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bank/list"));

        assertEquals("updateBankName", bankAccount.getName());
        assertEquals("updateBankNumber", bankAccount.getAccountNumber());
        assertEquals(PaymentCode.SHIN_HAN, bankAccount.getPaymentCode());
    }

    @Test
    void Post_계좌_업데이트_유저_고유번호_다름() throws Exception {
        User user = new User("testName", "testPassword", "testPhone");
        userRepository.save(user);

        BankAccount bankAccount = new BankAccount(user, "testBankName", "testBankNumber", PaymentCode.KB);
        bankAccountRepository.save(bankAccount);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId() + 1L);

        MockHttpServletRequestBuilder builder = post("/bank/update")
                .param("bankAccountId", String.valueOf(bankAccount.getId()))
                .param("name", "updateBankName")
                .param("accountNumber", "updateBankNumber")
                .param("paymentCode", "088")
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/error/error-page"));

        assertNotEquals("updateBankName", bankAccount.getName());
        assertNotEquals("updateBankNumber", bankAccount.getAccountNumber());
        assertNotEquals(PaymentCode.SHIN_HAN, bankAccount.getPaymentCode());
    }

    @Test
    void Post_계좌_업데이트_계좌_고유번호_다름() throws Exception {
        User user = new User("testName", "testPassword", "testPhone");
        userRepository.save(user);

        BankAccount bankAccount = new BankAccount(user, "testBankName", "testBankNumber", PaymentCode.KB);
        bankAccountRepository.save(bankAccount);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = post("/bank/update")
                .param("bankAccountId", String.valueOf(bankAccount.getId()) + 1L)
                .param("name", "updateBankName")
                .param("accountNumber", "updateBankNumber")
                .param("paymentCode", "088")
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/error/error-page"));

        assertNotEquals("updateBankName", bankAccount.getName());
        assertNotEquals("updateBankNumber", bankAccount.getAccountNumber());
        assertNotEquals(PaymentCode.SHIN_HAN, bankAccount.getPaymentCode());
    }
}