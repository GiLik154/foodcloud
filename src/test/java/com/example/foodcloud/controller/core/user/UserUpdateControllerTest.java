package com.example.foodcloud.controller.core.user;

import com.example.foodcloud.controller.core.user.UserUpdateController;
import com.example.foodcloud.enums.KoreanErrorCode;
import com.example.foodcloud.controller.advice.UserExceptionAdvice;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserUpdateControllerTest {
    private final UserUpdateController userUpdateController;
    private final UserExceptionAdvice userExceptionAdvice;
    private final UserRepository userRepository;
    private MockMvc mockMvc;

    @Autowired
    public UserUpdateControllerTest(UserUpdateController userUpdateController, UserExceptionAdvice userExceptionAdvice, UserRepository userRepository) {
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
    void ??????_??????_????????????() throws Exception {
        MockHttpServletRequestBuilder builder = get("/user/update");

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/user/update"));
    }

    @Test
    void ??????_??????_????????????() throws Exception {
        User user = new User("testName", "testPassword", "testPhone");
        userRepository.save(user);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = post("/user/update")
                .session(session)
                .param("phone", "updatePhone");

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/user/update"));

        assertEquals("updatePhone", user.getPhone());
    }

    @Test
    void ??????_??????_??????_????????????_??????() throws Exception {
        User user = new User("testName", "testPassword", "testPhone");
        userRepository.save(user);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId() + 1L);

        MockHttpServletRequestBuilder builder = post("/user/update")
                .session(session)
                .param("phone", "updatePhone");

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/error/error-page"))
                .andExpect(model().attribute("errorMsg", KoreanErrorCode.USER_INFO_NOT_FOUND.getResult()));

        assertEquals("testPhone", user.getPhone());
    }
}