package com.example.foodcloud.controller.user;

import com.example.foodcloud.controller.advice.KoreanErrorCode;
import com.example.foodcloud.controller.advice.UserExceptionAdvice;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserLoginControllerTest {
    private final UserLoginController userLoginController;
    private final UserExceptionAdvice userExceptionAdvice;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private MockMvc mockMvc;

    @Autowired
    public UserLoginControllerTest(UserLoginController userLoginController, UserExceptionAdvice userExceptionAdvice, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userLoginController = userLoginController;
        this.userExceptionAdvice = userExceptionAdvice;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userLoginController)
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

    @Test
    void 유저_로그인_정상작동() throws Exception {
        User user = new User("testName", passwordEncoder.encode("testPassword"), "testPhone");
        userRepository.save(user);

        MockHttpServletRequestBuilder builder = post("/user/login")
                .param("name", "testName")
                .param("password", "testPassword");

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/user/login"))
                .andExpect(model().attribute("userId", user.getId()));
    }

    @Test
    void 유저_로그인_아이디_다름() throws Exception {
        User user = new User("testName", passwordEncoder.encode("testPassword"), "testPhone");
        userRepository.save(user);

        MockHttpServletRequestBuilder builder = post("/user/login")
                .param("name", "wrongName")
                .param("password", "testPassword");

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/error/error-page"))
                .andExpect(model().attribute("errorMsg", KoreanErrorCode.USER_INFO_NOT_FOUND));
    }

    @Test
    void 유저_로그인_비밀번호_다름() throws Exception {
        User user = new User("testName", passwordEncoder.encode("testPassword"), "testPhone");
        userRepository.save(user);

        MockHttpServletRequestBuilder builder = post("/user/login")
                .param("name", "testName")
                .param("password", "wrongPassword");

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/error/error-page"))
                .andExpect(model().attribute("errorMsg", KoreanErrorCode.USER_INFO_NOT_FOUND));
    }

    @Test
    void 유저_로그인_아이디비밀번호_다름() throws Exception {
        User user = new User("testName", passwordEncoder.encode("testPassword"), "testPhone");
        userRepository.save(user);

        MockHttpServletRequestBuilder builder = post("/user/login")
                .param("name", "wrongName")
                .param("password", "wrongPassword");

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/error/error-page"))
                .andExpect(model().attribute("errorMsg", KoreanErrorCode.USER_INFO_NOT_FOUND));
    }
}