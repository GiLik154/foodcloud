package com.example.foodcloud.controller.core.bank;

import com.example.foodcloud.controller.advice.UserExceptionAdvice;
import com.example.foodcloud.controller.core.bank.dto.BankAccountAddControllerDto;
import com.example.foodcloud.controller.core.bank.dto.BankAccountUpdateControllerDto;
import com.example.foodcloud.controller.interceptor.LoginInterceptor;
import com.example.foodcloud.domain.payment.bank.domain.BankAccount;
import com.example.foodcloud.domain.payment.bank.domain.BankAccountRepository;
import com.example.foodcloud.domain.payment.bank.service.account.add.dto.BankAccountAddServiceDto;
import com.example.foodcloud.domain.payment.bank.service.account.update.dto.BankAccountUpdateServiceDto;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
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
    private MockMvc mockMvc;

    @Autowired
    public BankUpdatePostControllerTest(BankUpdateController bankUpdateController,
                                        UserExceptionAdvice userExceptionAdvice,
                                        LoginInterceptor loginInterceptor,
                                        UserRepository userRepository,
                                        BankAccountRepository bankAccountRepository) {

        this.bankUpdateController = bankUpdateController;
        this.userExceptionAdvice = userExceptionAdvice;
        this.loginInterceptor = loginInterceptor;
        this.userRepository = userRepository;
        this.bankAccountRepository = bankAccountRepository;
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(bankUpdateController)
                .setControllerAdvice(userExceptionAdvice)
                .addInterceptors(loginInterceptor)
                .build();
    }

    @Test
    void Post_계좌_업데이트_정상작동() throws Exception {
        User user = new User("testName", "testPassword", "testPhone");
        userRepository.save(user);

        BankAccount bankAccount = new BankAccount("testBankName", "testBankNumber", "001", user);
        bankAccountRepository.save(bankAccount);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = post("/bank/update")
                .param("bankAccountId", String.valueOf(bankAccount.getId()))
                .param("name", "updateBankName")
                .param("accountNumber", "updateBankNumber")
                .param("bank", "088")
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bank/list?isUpdate=true"))
                .andExpect(model().attribute("isUpdate", true));

        assertEquals("updateBankName", bankAccount.getName());
        assertEquals("updateBankNumber", bankAccount.getAccountNumber());
        assertEquals("088", bankAccount.getBank());
    }

    @Test
    void Post_계좌_업데이트_유저_고유번호_다름() throws Exception {
        User user = new User("testName", "testPassword", "testPhone");
        userRepository.save(user);

        BankAccount bankAccount = new BankAccount("testBankName", "testBankNumber", "001", user);
        bankAccountRepository.save(bankAccount);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId() + 1L);

        MockHttpServletRequestBuilder builder = post("/bank/update")
                .param("bankAccountId", String.valueOf(bankAccount.getId()))
                .param("name", "updateBankName")
                .param("accountNumber", "updateBankNumber")
                .param("bank", "088")
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bank/list?isUpdate=false"))
                .andExpect(model().attribute("isUpdate", false));

        assertNotEquals("updateBankName", bankAccount.getName());
        assertNotEquals("updateBankNumber", bankAccount.getAccountNumber());
        assertNotEquals("088", bankAccount.getBank());
    }

    @Test
    void Post_계좌_업데이트_계좌_고유번호_다름() throws Exception {
        User user = new User("testName", "testPassword", "testPhone");
        userRepository.save(user);

        BankAccount bankAccount = new BankAccount("testBankName", "testBankNumber", "001", user);
        bankAccountRepository.save(bankAccount);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = post("/bank/update")
                .param("bankAccountId", String.valueOf(bankAccount.getId()) + 1L)
                .param("name", "updateBankName")
                .param("accountNumber", "updateBankNumber")
                .param("bank", "088")
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bank/list?isUpdate=false"))
                .andExpect(model().attribute("isUpdate", false));

        assertNotEquals("updateBankName", bankAccount.getName());
        assertNotEquals("updateBankNumber", bankAccount.getAccountNumber());
        assertNotEquals("088", bankAccount.getBank());
    }
}