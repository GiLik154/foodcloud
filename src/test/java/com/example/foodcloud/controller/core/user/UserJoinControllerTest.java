package com.example.foodcloud.controller.core.user;

import com.example.foodcloud.controller.core.user.UserJoinController;
import com.example.foodcloud.enums.KoreanErrorCode;
import com.example.foodcloud.controller.advice.ParamValidateAdvice;
import com.example.foodcloud.controller.advice.UserExceptionAdvice;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
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
class UserJoinControllerTest {
    private final UserJoinController userJoinController;
    private final ParamValidateAdvice paramValidateAdvice;
    private final UserExceptionAdvice userExceptionAdvice;
    private final UserRepository userRepository;
    private MockMvc mockMvc;

    @Autowired
    public UserJoinControllerTest(UserJoinController userJoinController, ParamValidateAdvice paramValidateAdvice, UserExceptionAdvice userExceptionAdvice, UserRepository userRepository) {
        this.userJoinController = userJoinController;
        this.paramValidateAdvice = paramValidateAdvice;
        this.userExceptionAdvice = userExceptionAdvice;
        this.userRepository = userRepository;
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userJoinController)
                .setControllerAdvice(paramValidateAdvice, userExceptionAdvice)
                .build();
    }

    @Test
    void 유저_회원가입_기본페이지() throws Exception {
        MockHttpServletRequestBuilder builder = get("/user/join");

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/user/login"));
    }

    @Test
    void 유저_회원가입_정상작동() throws Exception {
        MockHttpServletRequestBuilder builder = post("/user/join")
                .param("joinName", "testName")
                .param("joinPassword", "testPassword")
                .param("joinPhone", "testPhone");


        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/user/join-check"));

        User user = userRepository.findByName("testName").get();

        assertNotNull(user.getId());
        assertEquals("testName", user.getName());
        assertNotEquals("testPassword", user.getPassword());
        assertEquals("testPhone", user.getPhone());
    }

    @Test
    void 유저_회원가입_아이디중복() throws Exception {
        User user = new User("testName", "testPassword", "testPhone");
        userRepository.save(user);

        MockHttpServletRequestBuilder builder = post("/user/join")
                .param("joinName", "testName")
                .param("joinPassword", "testPassword")
                .param("joinPhone", "testPhone");

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/error/error-page"))
                .andExpect(model().attribute("errorMsg", KoreanErrorCode.USER_NAME_DUPLICATE.getResult()));
    }

    @Test
    void 유저_회원가입_파라미터_Null() throws Exception {
        User user = new User("testName", "testPassword", "testPhone");
        userRepository.save(user);

        MockHttpServletRequestBuilder builder = post("/user/join")
                .param("joinName", "")
                .param("joinPassword", "")
                .param("joinPhone", "");

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/error/error-page"))
                .andExpect(model().attribute("errorMsg", KoreanErrorCode.METHOD_ARGUMENT.getResult()));
    }
}