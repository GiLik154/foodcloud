package com.example.foodcloud.controller.core.payment;

import com.example.foodcloud.controller.advice.NotFoundExceptionAdvice;
import com.example.foodcloud.controller.advice.PointExceptionAdvice;
import com.example.foodcloud.controller.advice.UserExceptionAdvice;
import com.example.foodcloud.controller.interceptor.LoginInterceptor;
import com.example.foodcloud.domain.bank.domain.BankAccount;
import com.example.foodcloud.domain.bank.domain.BankAccountRepository;
import com.example.foodcloud.domain.point.domain.Point;
import com.example.foodcloud.domain.point.domain.PointRepository;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import com.example.foodcloud.enums.KoreanErrorCode;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PayControllerTest {
    private final PayController payController;
    private final UserRepository userRepository;
    private final BankAccountRepository bankAccountRepository;
    private final PointRepository pointRepository;
    private final LoginInterceptor loginInterceptor;
    private final UserExceptionAdvice userExceptionAdvice;
    private final NotFoundExceptionAdvice notFoundExceptionAdvice;
    private final PointExceptionAdvice pointExceptionAdvice;
    private MockMvc mockMvc;

    @Autowired
    public PayControllerTest(PayController payController, UserRepository userRepository, BankAccountRepository bankAccountRepository, PointRepository pointRepository, LoginInterceptor loginInterceptor, UserExceptionAdvice userExceptionAdvice, NotFoundExceptionAdvice notFoundExceptionAdvice, PointExceptionAdvice pointExceptionAdvice) {
        this.payController = payController;
        this.userRepository = userRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.pointRepository = pointRepository;
        this.loginInterceptor = loginInterceptor;
        this.userExceptionAdvice = userExceptionAdvice;
        this.notFoundExceptionAdvice = notFoundExceptionAdvice;
        this.pointExceptionAdvice = pointExceptionAdvice;
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(payController)
                .setControllerAdvice(userExceptionAdvice, notFoundExceptionAdvice, pointExceptionAdvice)
                .addInterceptors(loginInterceptor)
                .build();
    }
    @Test
    void ??????_??????_??????() throws Exception {
        User user = new User("testUserName", "testPassword", "testPhone");
        userRepository.save(user);

        BankAccount bankAccount = new BankAccount("test", "test", "011", user);
        bankAccountRepository.save(bankAccount);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = post("/payment/pay")
                .param("bank", "004")
                .param("bankAccountId", String.valueOf(bankAccount.getId()))
                .param("price", String.valueOf(5000))
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/payment/pay"))
                .andExpect(model().attribute("payment", "5000 price KB Bank payment succeed"));
    }

    @Test
    void ??????_??????_??????() throws Exception {
        User user = new User("testUserName", "testPassword", "testPhone");
        userRepository.save(user);

        BankAccount bankAccount = new BankAccount("test", "test", "011", user);
        bankAccountRepository.save(bankAccount);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = post("/payment/pay")
                .param("bank", "011")
                .param("bankAccountId", String.valueOf(bankAccount.getId()))
                .param("price", String.valueOf(5000))
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/payment/pay"))
                .andExpect(model().attribute("payment", "5000 price NH bank payment succeed"));
    }

    @Test
    void ??????_??????_??????() throws Exception {
        User user = new User("testUserName", "testPassword", "testPhone");
        userRepository.save(user);

        BankAccount bankAccount = new BankAccount("test", "test", "011", user);
        bankAccountRepository.save(bankAccount);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = post("/payment/pay")
                .param("bank", "088")
                .param("bankAccountId", String.valueOf(bankAccount.getId()))
                .param("price", String.valueOf(5000))
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/payment/pay"))
                .andExpect(model().attribute("payment", "5000 price ShinHan bank payment succeed"));
    }

    @Test
    void ??????_????????????_??????() throws Exception {
        User user = new User("testUserName", "testPassword", "testPhone");
        userRepository.save(user);

        BankAccount bankAccount = new BankAccount("test", "test", "011", user);
        bankAccountRepository.save(bankAccount);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = post("/payment/pay")
                .param("bank", "088")
                .param("bankAccountId", String.valueOf(bankAccount.getId() + 1L))
                .param("price", String.valueOf(5000))
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/payment/pay"))
                .andExpect(model().attribute("payment", "ShinHan bank payment fail"));
    }

    @Test
    void ??????_??????_??????() throws Exception {
        User user = new User("testUserName", "testPassword", "testPhone");
        userRepository.save(user);

        BankAccount bankAccount = new BankAccount("test", "test", "011", user);
        bankAccountRepository.save(bankAccount);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = post("/payment/pay")
                .param("bank", "099")
                .param("bankAccountId", String.valueOf(bankAccount.getId()))
                .param("price", String.valueOf(5000))
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/error/error-page"))
                .andExpect(model().attribute("errorMsg", KoreanErrorCode.BANK_NOT_FOUND.getResult()));
    }

    @Test
    void ??????_??????_??????() throws Exception {
        User user = new User("testUserName", "testPassword", "testPhone");
        userRepository.save(user);

        BankAccount bankAccount = new BankAccount("test", "test", "011", user);
        bankAccountRepository.save(bankAccount);

        MockHttpServletRequestBuilder builder = post("/payment/pay")
                .param("bank", "088")
                .param("bankAccountId", String.valueOf(bankAccount.getId()))
                .param("price", String.valueOf(5000));

        mockMvc.perform(builder)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/login"));
    }

    @Test
    void ??????_??????_??????_????????????_??????() throws Exception {
        User user = new User("testUserName", "testPassword", "testPhone");
        userRepository.save(user);

        BankAccount bankAccount = new BankAccount("test", "test", "011", user);
        bankAccountRepository.save(bankAccount);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId() + 1L);

        MockHttpServletRequestBuilder builder = post("/payment/pay")
                .param("bank", "088")
                .param("bankAccountId", String.valueOf(bankAccount.getId()))
                .param("price", String.valueOf(5000))
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/payment/pay"))
                .andExpect(model().attribute("payment", "ShinHan bank payment fail"));
    }

    @Test
    void ?????????_??????_??????() throws Exception {
        User user = new User("testUserName", "testPassword", "testPhone");
        userRepository.save(user);

        Point point = new Point(user);
        point.updatePoint(6000);
        pointRepository.save(point);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = post("/payment/pay")
                .param("bank", "000")
                .param("bankAccountId", String.valueOf(0))
                .param("price", String.valueOf(5000))
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/payment/pay"))
                .andExpect(model().attribute("payment", "5000 price Point payment succeed"));

        assertEquals(1000, point.getTotalPoint());
    }

    @Test
    void ?????????_??????_????????????() throws Exception {
        User user = new User("testUserName", "testPassword", "testPhone");
        userRepository.save(user);

        Point point = new Point(user);
        point.updatePoint(3000);
        pointRepository.save(point);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = post("/payment/pay")
                .param("bank", "000")
                .param("bankAccountId", String.valueOf(0))
                .param("price", String.valueOf(5000))
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/error/error-page"))
                .andExpect(model().attribute("errorMsg", KoreanErrorCode.NOT_ENOUGH_POINT.getResult()));
    }
}