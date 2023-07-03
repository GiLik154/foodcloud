package com.example.foodcloud.controller.core.user.mypage;

import com.example.foodcloud.controller.advice.UserExceptionAdvice;
import com.example.foodcloud.controller.core.user.UserMyPageController;
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
class UserServiceMyPageControllerTest {
    private final UserMyPageController userMyPageController;
    private final UserExceptionAdvice userExceptionAdvice;
    private final UserRepository userRepository;
    private final LoginInterceptor loginInterceptor;
    private MockMvc mockMvc;

    @Autowired
    public UserServiceMyPageControllerTest(UserMyPageController userMyPageController, UserExceptionAdvice userExceptionAdvice, UserRepository userRepository, LoginInterceptor loginInterceptor) {
        this.userMyPageController = userMyPageController;
        this.userExceptionAdvice = userExceptionAdvice;
        this.userRepository = userRepository;
        this.loginInterceptor = loginInterceptor;
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userMyPageController)
                .setControllerAdvice(userExceptionAdvice)
                .addInterceptors(loginInterceptor)
                .build();
    }

    @Test
    void 마이페이지_기본화면() throws Exception {
        User user = new User("testName", "testPassword", "testPhone");
        userRepository.save(user);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = get("/user/my-page")
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/user/my-page"))
                .andExpect(model().attribute("userInfo", user));
    }

    @Test
    void 마이페이지_세션없음() throws Exception {
        MockHttpServletRequestBuilder builder = get("/user/my-page");

        mockMvc.perform(builder)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/login"));
    }
}