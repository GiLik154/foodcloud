package com.example.foodcloud.controller.core.user;

import com.example.foodcloud.UserFixture;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import com.example.foodcloud.enums.KoreanErrorCode;
import com.example.foodcloud.security.login.UserDetail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserDeletePostControllerTest {
    private final WebApplicationContext context;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private MockMvc mockMvc;

    @Autowired
    public UserDeletePostControllerTest(WebApplicationContext context, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.context = context;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    void 유저_삭제_정상작동() throws Exception {
        User user = userRepository.save(UserFixture.fixture().encoding(passwordEncoder, "testPassword").build());

        UserDetail userDetail = new UserDetail(user.getName(), user.getPassword(), user.getUserGrade());
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        MockHttpServletRequestBuilder builder = delete("/user/delete")
                .with(csrf())
                .param("name", "testUserName")
                .param("password", "testPassword");

        mockMvc.perform(builder)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/my-page"));

        assertFalse(userRepository.existsByName("testUserName"));
    }

    @Test
    void 유저_아이다가_다르면_익셉션_발생() throws Exception {
        User user = userRepository.save(UserFixture.fixture().encoding(passwordEncoder, "testPassword").build());

        UserDetail userDetail = new UserDetail(user.getName(), user.getPassword(), user.getUserGrade());
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        MockHttpServletRequestBuilder builder = delete("/user/delete")
                .with(csrf())
                .param("password", "testPassword");

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(view().name("thymeleaf/error/error-page"))
                .andExpect(model().attribute("errorMsg", KoreanErrorCode.USER_INFO_NOT_FOUND.getResult()));

        assertTrue(userRepository.existsByName("testUserName"));
    }

    @Test
    void Post_유저_삭제_정상_아이디_다름() throws Exception {
        User user = userRepository.save(UserFixture.fixture().encoding(passwordEncoder, "testPassword").build());

        UserDetail userDetail = new UserDetail(user.getName(), user.getPassword(), user.getUserGrade());
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        MockHttpServletRequestBuilder builder = delete("/user/delete")
                .with(csrf())
                .param("name", "wrongName")
                .param("password", "testPassword");

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(view().name("thymeleaf/error/error-page"))
                .andExpect(model().attribute("errorMsg", KoreanErrorCode.USER_INFO_NOT_FOUND.getResult()));

        assertTrue(userRepository.existsByName("testUserName"));
    }

    @Test
    void Post_유저_삭제_정상_비밀번호_다름() throws Exception {
        User user = userRepository.save(UserFixture.fixture().encoding(passwordEncoder, "testPassword").build());

        UserDetail userDetail = new UserDetail(user.getName(), user.getPassword(), user.getUserGrade());
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        MockHttpServletRequestBuilder builder = delete("/user/delete")
                .with(csrf())
                .param("name", "testUserName")
                .param("password", "wrongPassword");

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(view().name("thymeleaf/error/error-page"))
                .andExpect(model().attribute("errorMsg", KoreanErrorCode.USER_INFO_NOT_FOUND.getResult()));

        assertTrue(userRepository.existsByName("testUserName"));
    }
}