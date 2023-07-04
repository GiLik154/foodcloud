package com.example.foodcloud.controller.core.user.login;

import com.example.foodcloud.controller.core.user.UserLoginController;
import com.example.foodcloud.controller.advice.UserExceptionAdvice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserLoginGetControllerTest {
    private final UserLoginController userLoginController;
    private final UserExceptionAdvice userExceptionAdvice;
    private MockMvc mockMvc;

    UserLoginGetControllerTest(UserLoginController userLoginController, UserExceptionAdvice userExceptionAdvice) {
        this.userLoginController = userLoginController;
        this.userExceptionAdvice = userExceptionAdvice;
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userLoginController)
                .apply(springSecurity())
                .setControllerAdvice(userExceptionAdvice)
                .build();
    }

    @Test
    void 유저_로그인_기본페이지() throws Exception {
        MockHttpServletRequestBuilder builder = get("/user/login");

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/user/login"));
    }
}