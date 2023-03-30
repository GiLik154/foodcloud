package com.example.foodcloud.controller.core.bank;

import com.example.foodcloud.controller.interceptor.LoginInterceptor;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BankAddGetControllerTest {
    private final BankAddController bankAddController;
    private final UserRepository userRepository;
    private final LoginInterceptor loginInterceptor;
    private MockMvc mockMvc;

    @Autowired
    public BankAddGetControllerTest(BankAddController bankAddController, UserRepository userRepository, LoginInterceptor loginInterceptor) {
        this.bankAddController = bankAddController;
        this.userRepository = userRepository;
        this.loginInterceptor = loginInterceptor;
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(bankAddController)
                .addInterceptors(loginInterceptor)
                .build();
    }

    @Test
    void 계좌_추가_Get_정상작동() throws Exception {
        User user = new User("testUserName", "testPassword", "testPhone");
        userRepository.save(user);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = get("/bank/add")
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/bank/add"));
    }

    @Test
    void 계좌_추가_Get_세션_없음() throws Exception {
        User user = new User("testUserName", "testPassword", "testPhone");
        userRepository.save(user);

        MockHttpServletRequestBuilder builder = get("/bank/add");

        mockMvc.perform(builder)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/login"));
    }

}