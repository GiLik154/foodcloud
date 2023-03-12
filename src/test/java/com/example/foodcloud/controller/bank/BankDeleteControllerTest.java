package com.example.foodcloud.controller.bank;

import com.example.foodcloud.controller.advice.KoreanErrorCode;
import com.example.foodcloud.controller.advice.UserExceptionAdvice;
import com.example.foodcloud.controller.interceptor.LoginInterceptor;
import com.example.foodcloud.domain.bank.domain.BankAccount;
import com.example.foodcloud.domain.bank.domain.BankAccountRepository;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
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
class BankDeleteControllerTest {
    private final BankDeleteController bankDeleteController;
    private final UserExceptionAdvice userExceptionAdvice;
    private final LoginInterceptor loginInterceptor;
    private final UserRepository userRepository;
    private final BankAccountRepository bankAccountRepository;
    private final PasswordEncoder passwordEncoder;
    private MockMvc mockMvc;

    @Autowired
    public BankDeleteControllerTest(BankDeleteController bankDeleteController, UserExceptionAdvice userExceptionAdvice, LoginInterceptor loginInterceptor, UserRepository userRepository, BankAccountRepository bankAccountRepository, PasswordEncoder passwordEncoder) {
        this.bankDeleteController = bankDeleteController;
        this.userExceptionAdvice = userExceptionAdvice;
        this.loginInterceptor = loginInterceptor;
        this.userRepository = userRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(bankDeleteController)
                .setControllerAdvice(userExceptionAdvice)
                .addInterceptors(loginInterceptor)
                .build();
    }

    @Test
    void 계좌_삭제_정상작동() throws Exception {
        User user = new User("testName", passwordEncoder.encode("testPassword"), "testPhone");
        userRepository.save(user);

        BankAccount bankAccount = new BankAccount("test", "test", "001", user);
        bankAccountRepository.save(bankAccount);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = post("/bank/delete")
                .param("password", "testPassword")
                .param("bankAccountId", String.valueOf(bankAccount.getId()))
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/bank/delete"));

        assertFalse(bankAccountRepository.existsById(bankAccount.getId()));
    }

    @Test
    void 계좌_삭제_세션_없음() throws Exception {
        User user = new User("testName", passwordEncoder.encode("testPassword"), "testPhone");
        userRepository.save(user);

        BankAccount bankAccount = new BankAccount("test", "test", "001", user);
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
    void 계좌_삭제_유저_아이디_다름() throws Exception {
        User user = new User("testName", passwordEncoder.encode("testPassword"), "testPhone");
        userRepository.save(user);

        BankAccount bankAccount = new BankAccount("test", "test", "001", user);
        bankAccountRepository.save(bankAccount);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId() + 1L);

        System.out.println(user.getId());

        MockHttpServletRequestBuilder builder = post("/bank/delete")
                .param("password", "testPassword")
                .param("bankAccountId", String.valueOf(bankAccount.getId()))
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/error/error-page"))
                .andExpect(model().attribute("errorMsg", KoreanErrorCode.USER_INFO_NOT_FOUND));

        assertTrue(bankAccountRepository.existsById(bankAccount.getId()));
    }

    @Test
    void 계좌_삭제_유저_비밀번호_다름() throws Exception {
        User user = new User("testName", passwordEncoder.encode("testPassword"), "testPhone");
        userRepository.save(user);

        BankAccount bankAccount = new BankAccount("test", "test", "001", user);
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
                .andExpect(model().attribute("errorMsg", KoreanErrorCode.USER_INFO_NOT_FOUND));

        assertTrue(bankAccountRepository.existsById(bankAccount.getId()));
    }

    @Test
    void 계좌_삭제_계좌_아이디_다름() throws Exception {
        User user = new User("testName", passwordEncoder.encode("testPassword"), "testPhone");
        userRepository.save(user);

        BankAccount bankAccount = new BankAccount("test", "test", "001", user);
        bankAccountRepository.save(bankAccount);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = post("/bank/delete")
                .param("password", "testPassword")
                .param("bankAccountId", String.valueOf(bankAccount.getId() + 1L))
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/error/error-page"))
                .andExpect(model().attribute("errorMsg", KoreanErrorCode.NOT_FOUND_BANK));

        assertTrue(bankAccountRepository.existsById(bankAccount.getId()));
    }
}