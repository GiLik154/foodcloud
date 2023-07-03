package com.example.foodcloud.controller.core.user.delete;

import com.example.foodcloud.controller.core.user.UserDeleteController;
import com.example.foodcloud.controller.advice.UserExceptionAdvice;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserServiceDeleteGetControllerTest {
    private final UserDeleteController userDeleteController;
    private final UserExceptionAdvice userExceptionAdvice;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private MockMvc mockMvc;

    @Autowired
    public UserServiceDeleteGetControllerTest(UserDeleteController userDeleteController, UserExceptionAdvice userExceptionAdvice, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userDeleteController = userDeleteController;
        this.userExceptionAdvice = userExceptionAdvice;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userDeleteController)
                .setControllerAdvice(userExceptionAdvice)
                .build();
    }

    @Test
    void 유저_삭제_기본페이지() throws Exception {
        User user = new User("testName", passwordEncoder.encode("testPassword"), "testPhone");
        userRepository.save(user);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = get("/user/delete")
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/user/delete"));
    }
}