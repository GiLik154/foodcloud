package com.example.foodcloud.controller.core.bank;

import com.example.foodcloud.controller.advice.NotFoundExceptionAdvice;
import com.example.foodcloud.enums.KoreanErrorCode;
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
import org.springframework.security.crypto.password.PasswordEncoder;
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
class BankDeletePostControllerTest {
    private final BankDeleteController bankDeleteController;
    private final UserExceptionAdvice userExceptionAdvice;
    private final UserRepository userRepository;
    private final BankAccountRepository bankAccountRepository;
    private final PasswordEncoder passwordEncoder;

    private final NotFoundExceptionAdvice notFoundExceptionAdvice;

    private MockMvc mockMvc;

    @Autowired
    public BankDeletePostControllerTest(BankDeleteController bankDeleteController, UserExceptionAdvice userExceptionAdvice, UserRepository userRepository, BankAccountRepository bankAccountRepository, PasswordEncoder passwordEncoder, NotFoundExceptionAdvice notFoundExceptionAdvice) {
        this.bankDeleteController = bankDeleteController;
        this.userExceptionAdvice = userExceptionAdvice;
        this.userRepository = userRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.passwordEncoder = passwordEncoder;
        this.notFoundExceptionAdvice = notFoundExceptionAdvice;
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(bankDeleteController)
                .setControllerAdvice(userExceptionAdvice, notFoundExceptionAdvice)
                .build();
    }

    @Test
    void Post_계좌_삭제_정상작동() throws Exception {
        User user = new User("testName", passwordEncoder.encode("testPassword"), "testPhone");
        userRepository.save(user);

        BankAccount bankAccount = new BankAccount(user, "testBankName", "testBankNumber", PaymentCode.KB);
        bankAccountRepository.save(bankAccount);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = post("/bank/delete")
                .param("password", "testPassword")
                .param("bankAccountId", String.valueOf(bankAccount.getId()))
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bank/list"));

        assertFalse(bankAccountRepository.existsById(bankAccount.getId()));
    }

    @Test
    void Post_계좌_삭제_세션_없음() throws Exception {
        User user = new User("testName", passwordEncoder.encode("testPassword"), "testPhone");
        userRepository.save(user);

        BankAccount bankAccount = new BankAccount(user, "testBankName", "testBankNumber", PaymentCode.KB);
        bankAccountRepository.save(bankAccount);

        MockHttpServletRequestBuilder builder = post("/bank/delete")
                .param("password", "testPassword")
                .param("bankAccountId", String.valueOf(bankAccount.getId()));

        mockMvc.perform(builder)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/login"));

        assertTrue(bankAccountRepository.existsById(bankAccount.getId()));
    }

    @Test
    void Post_계좌_삭제_유저_아이디_다름() throws Exception {
        User user = new User("testName", passwordEncoder.encode("testPassword"), "testPhone");
        userRepository.save(user);

        BankAccount bankAccount = new BankAccount(user, "testBankName", "testBankNumber", PaymentCode.KB);
        bankAccountRepository.save(bankAccount);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId() + 1L);

        MockHttpServletRequestBuilder builder = post("/bank/delete")
                .param("password", "testPassword")
                .param("bankAccountId", String.valueOf(bankAccount.getId()))
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/error/error-page"));

        assertTrue(bankAccountRepository.existsById(bankAccount.getId()));
    }

    @Test
    void Post_계좌_삭제_유저_비밀번호_다름() throws Exception {
        User user = new User("testName", passwordEncoder.encode("testPassword"), "testPhone");
        userRepository.save(user);

        BankAccount bankAccount = new BankAccount(user, "testBankName", "testBankNumber", PaymentCode.KB);
        bankAccountRepository.save(bankAccount);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = post("/bank/delete")
                .param("password", "wrongPassword")
                .param("bankAccountId", String.valueOf(bankAccount.getId()))
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/error/error-page"))
                .andExpect(model().attribute("errorMsg", KoreanErrorCode.USER_INFO_NOT_FOUND.getResult()));

        assertTrue(bankAccountRepository.existsById(bankAccount.getId()));
    }

    @Test
    void Post_계좌_삭제_계좌_아이디_다름() throws Exception {
        User user = new User("testName", passwordEncoder.encode("testPassword"), "testPhone");
        userRepository.save(user);

        BankAccount bankAccount = new BankAccount(user, "testBankName", "testBankNumber", PaymentCode.KB);
        bankAccountRepository.save(bankAccount);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = post("/bank/delete")
                .param("password", "testPassword")
                .param("bankAccountId", String.valueOf(bankAccount.getId() + 1L))
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/error/error-page"));

        assertTrue(bankAccountRepository.existsById(bankAccount.getId()));
    }
}