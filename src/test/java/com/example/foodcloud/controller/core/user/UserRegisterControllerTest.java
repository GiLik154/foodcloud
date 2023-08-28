package com.example.foodcloud.controller.core.user;

import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import com.example.foodcloud.enums.KoreanErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRegisterControllerTest {
    private final WebApplicationContext context;
    private final UserRepository userRepository;
    private MockMvc mockMvc;

    @Autowired
    public UserRegisterControllerTest(WebApplicationContext context, UserRepository userRepository) {
        this.context = context;
        this.userRepository = userRepository;
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    void Get_유저_회원가입_기본페이지() throws Exception {
        MockHttpServletRequestBuilder builder = get("/user/join");

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(view().name("thymeleaf/user/login"));
    }

    @Test
    void Post_유저_회원가입_정상작동() throws Exception {
        MockHttpServletRequestBuilder builder = post("/user")
                .with(csrf())
                .param("joinName", "testName")
                .param("joinPassword", "testPassword")
                .param("joinPhone", "testPhone");

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(view().name("thymeleaf/user/join-check"));

        User user = userRepository.findByName("testName").get();

        assertNotNull(user.getId());
        assertEquals("testName", user.getName());
        assertNotEquals("testPassword", user.getPassword());
        assertEquals("testPhone", user.getPhone());
    }

    @Test
    void Post_유저_회원가입_아이디중복() throws Exception {
        User user = new User("testName", "testPassword", "testPhone");
        userRepository.save(user);

        MockHttpServletRequestBuilder builder = post("/user")
                .with(csrf())
                .param("joinName", "testName")
                .param("joinPassword", "testPassword")
                .param("joinPhone", "testPhone");

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(view().name("thymeleaf/error/error-page"))
                .andExpect(model().attribute("errorMsg", KoreanErrorCode.USER_NAME_DUPLICATE.getResult()));
    }

    @Test
    void Post_유저_회원가입_파라미터_Null() throws Exception {
        User user = new User("testName", "testPassword", "testPhone");
        userRepository.save(user);

        MockHttpServletRequestBuilder builder = post("/user")
                .with(csrf())
                .param("joinName", "")
                .param("joinPassword", "")
                .param("joinPhone", "");

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(view().name("thymeleaf/error/error-page"))
                .andExpect(model().attribute("errorMsg", KoreanErrorCode.METHOD_ARGUMENT.getResult()));
    }
}