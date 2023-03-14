package com.example.foodcloud.controller.core.payment;

import com.example.foodcloud.controller.advice.NotFoundExceptionAdvice;
import com.example.foodcloud.controller.advice.ParamValidateAdvice;
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
import com.example.foodcloud.exception.NotEnoughPointException;
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
    private final ParamValidateAdvice paramValidateAdvice;
    private final PointExceptionAdvice pointExceptionAdvice;
    private MockMvc mockMvc;

    @Autowired
    public PayControllerTest(PayController payController, UserRepository userRepository, BankAccountRepository bankAccountRepository, PointRepository pointRepository, LoginInterceptor loginInterceptor, UserExceptionAdvice userExceptionAdvice, ParamValidateAdvice paramValidateAdvice, PointExceptionAdvice pointExceptionAdvice) {
        this.payController = payController;
        this.userRepository = userRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.pointRepository = pointRepository;
        this.loginInterceptor = loginInterceptor;
        this.userExceptionAdvice = userExceptionAdvice;
        this.paramValidateAdvice = paramValidateAdvice;
        this.pointExceptionAdvice = pointExceptionAdvice;
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(payController)
                .setControllerAdvice(userExceptionAdvice, paramValidateAdvice, pointExceptionAdvice)
                .addInterceptors(loginInterceptor)
                .build();
    }
    @Test
    void 국민_결제_완료() throws Exception {
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
    void 농협_결제_완료() throws Exception {
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
    void 신한_결제_완료() throws Exception {
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
    void 은행_고유번호_다름() throws Exception {
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
    void 은행_코드_다름() throws Exception { //todo 이거 NullPoint익셉션 말고 커스텀 입센션 만들어서 핸들링 하는거로.
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
                .andExpect(model().attribute("errorMsg", KoreanErrorCode.METHOD_ARGUMENT.getResult()));
    }

    @Test
    void 포인트_결제_완료() throws Exception {
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
    }

    @Test
    void 포인트_결제_잔액부족() throws Exception {
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