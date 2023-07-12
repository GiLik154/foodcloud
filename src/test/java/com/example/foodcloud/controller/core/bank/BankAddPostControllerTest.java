package com.example.foodcloud.controller.core.bank;

import com.example.foodcloud.controller.advice.UserExceptionAdvice;
import com.example.foodcloud.domain.payment.domain.BankAccount;
import com.example.foodcloud.domain.payment.domain.BankAccountRepository;
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
class BankAddPostControllerTest {
    private final BankAddController bankAddController;
    private final UserExceptionAdvice userExceptionAdvice;
    private final UserRepository userRepository;
    private final BankAccountRepository bankAccountRepository;
    private MockMvc mockMvc;

    @Autowired
    BankAddPostControllerTest(BankAddController bankAddController, UserExceptionAdvice userExceptionAdvice, UserRepository userRepository, BankAccountRepository bankAccountRepository) {
        this.bankAddController = bankAddController;
        this.userExceptionAdvice = userExceptionAdvice;
        this.userRepository = userRepository;
        this.bankAccountRepository = bankAccountRepository;
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(bankAddController)
                .setControllerAdvice(userExceptionAdvice)
                .build();
    }

    @Test
    void Post_계좌_추가_정상작동() throws Exception {
        User user = new User("testName", "testPassword", "testPhone");
        userRepository.save(user);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = post("/bank/add")
                .param("name", "testName")
                .param("accountNumber", "testNumber")
                .param("paymentCode", "004")
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/bank/add"));

        BankAccount bankAccount = bankAccountRepository.findByUserId(user.getId()).get(0);

        assertNotNull(bankAccount.getId());
        assertEquals("testName", bankAccount.getName());
        assertEquals("testNumber", bankAccount.getAccountNumber());
        assertEquals(PaymentCode.KB, bankAccount.getPaymentCode());

    }

    @Test
    void Post_계좌_추가_유저아이디_없음() throws Exception {
        User user = new User("testName", "testPassword", "testPhone");
        userRepository.save(user);

        MockHttpServletRequestBuilder builder = post("/bank/add")
                .param("name", "testName")
                .param("accountNumber", "testNumber")
                .param("paymentCode", "004");

        mockMvc.perform(builder)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/login"));
    }
}