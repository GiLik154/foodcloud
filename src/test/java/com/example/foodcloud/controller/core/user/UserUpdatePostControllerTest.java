package com.example.foodcloud.controller.core.user;

import com.example.foodcloud.UserFixture;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import com.example.foodcloud.security.login.UserDetail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserUpdatePostControllerTest {
    private final WebApplicationContext context;
    private final UserRepository userRepository;
    private MockMvc mockMvc;

    @Autowired
    public UserUpdatePostControllerTest(WebApplicationContext context, UserRepository userRepository) {
        this.context = context;
        this.userRepository = userRepository;
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    void Post_유저_수정_정상작동() throws Exception {
        User user = userRepository.save(UserFixture.fixture().build());

        UserDetail userDetail = new UserDetail(user.getName(), user.getPassword(), user.getUserGrade());
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        MockHttpServletRequestBuilder builder = put("/user")
                .with(csrf())
                .param("phone", "updatePhone");

        mockMvc.perform(builder)

                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/my-page"));

        assertEquals("updatePhone", user.getPhone());
    }

    @Test
    void Post_유저_수정_유저_고유번호_다름() throws Exception {
        User user = userRepository.save(UserFixture.fixture().build());

        UserDetail userDetail = new UserDetail("wrongUserName", user.getPassword(), user.getUserGrade());
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        MockHttpServletRequestBuilder builder = put("/user")
                .with(csrf())
                .param("phone", "updatePhone");

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(view().name("thymeleaf/error/error-page"));

        assertEquals("testUserPhone", user.getPhone());
    }
}