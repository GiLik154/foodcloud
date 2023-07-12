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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BankListControllerTest {
    private final BankListController bankListController;
    private final UserExceptionAdvice userExceptionAdvice;
    private final UserRepository userRepository;
    private final BankAccountRepository bankAccountRepository;
    private MockMvc mockMvc;

    @Autowired
    public BankListControllerTest(BankListController bankListController, UserExceptionAdvice userExceptionAdvice, UserRepository userRepository, BankAccountRepository bankAccountRepository) {
        this.bankListController = bankListController;
        this.userExceptionAdvice = userExceptionAdvice;
        this.userRepository = userRepository;
        this.bankAccountRepository = bankAccountRepository;
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(bankListController)
                .setControllerAdvice(userExceptionAdvice)
                .build();
    }

    @Test
    void 계좌_리스트_정상출력() throws Exception {
        User user = new User("testName", "testPassword", "testPhone");
        userRepository.save(user);

        BankAccount bankAccount = new BankAccount(user, "testBankName", "testBankNumber", PaymentCode.KB);
        bankAccountRepository.save(bankAccount);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = get("/bank/list")
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/bank/list"))
                .andExpect(model().attribute("bankAccountList", bankAccountRepository.findByUserId(user.getId())));
    }
}