package com.example.foodcloud.controller.core.user;

import com.example.foodcloud.controller.advice.UserExceptionAdvice;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserUpdateGetControllerTest {
    private final UserUpdateController userUpdateController;
    private final UserExceptionAdvice userExceptionAdvice;
    private final UserRepository userRepository;
    private MockMvc mockMvc;

    @Autowired
    public UserUpdateGetControllerTest(UserUpdateController userUpdateController, UserExceptionAdvice userExceptionAdvice, UserRepository userRepository) {
        this.userUpdateController = userUpdateController;
        this.userExceptionAdvice = userExceptionAdvice;
        this.userRepository = userRepository;
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userUpdateController)
                .setControllerAdvice(userExceptionAdvice)
                .build();
    }

    @Test
    void Get_유저_수정_기본화면() throws Exception {
        User user = new User("testName", "testPassword", "testPhone");
        userRepository.save(user);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = get("/user/update")
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/user/update"));
    }
}