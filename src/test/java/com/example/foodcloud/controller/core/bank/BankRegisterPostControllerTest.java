package com.example.foodcloud.controller.core.bank;

import com.example.foodcloud.UserFixture;
import com.example.foodcloud.domain.payment.domain.BankAccount;
import com.example.foodcloud.domain.payment.domain.BankAccountRepository;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import com.example.foodcloud.enums.PaymentCode;
import com.example.foodcloud.security.login.UserDetail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BankRegisterPostControllerTest {
    private final WebApplicationContext context;
    private final UserRepository userRepository;
    private final BankAccountRepository bankAccountRepository;
    private MockMvc mockMvc;

    @Autowired
    public BankRegisterPostControllerTest(WebApplicationContext context, UserRepository userRepository, BankAccountRepository bankAccountRepository) {
        this.context = context;
        this.userRepository = userRepository;
        this.bankAccountRepository = bankAccountRepository;
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    void Post_계좌_추가_정상작동() throws Exception {
        User user = userRepository.save(UserFixture.fixture().build());

        UserDetail userDetail = new UserDetail(user.getName(), user.getPassword(), user.getUserGrade(), user.getId());
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        MockHttpServletRequestBuilder builder = post("/bank/register")
                .with(csrf())
                .param("name", "testName")
                .param("accountNumber", "testNumber")
                .param("paymentCode", "004");

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(view().name("thymeleaf/bank/register"));

        BankAccount bankAccount = bankAccountRepository.findByUserId(user.getId()).get(0);

        assertNotNull(bankAccount.getId());
        assertEquals("testName", bankAccount.getName());
        assertEquals("testNumber", bankAccount.getAccountNumber());
        assertEquals(PaymentCode.KB, bankAccount.getPaymentCode());

    }

    @Test
    @WithAnonymousUser
    void 로그인_안하면_접속_못함() throws Exception {
        MockHttpServletRequestBuilder builder = post("/bank/register")
                .with(csrf());

        mockMvc.perform(builder)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/user/login"));
    }
}