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
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserDeleteGetControllerTest {
    private final WebApplicationContext context;
    private final UserRepository userRepository;
    private MockMvc mockMvc;

    @Autowired
    public UserDeleteGetControllerTest(WebApplicationContext context, UserRepository userRepository) {
        this.context = context;
        this.userRepository = userRepository;
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    void 유저_삭제_기본페이지() throws Exception {
        User user = userRepository.save(UserFixture.fixture().build());

        UserDetail userDetail = new UserDetail(user.getName(), user.getPassword(), user.getUserGrade(), user.getId());
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        MockHttpServletRequestBuilder builder = get("/user/delete");

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(view().name("thymeleaf/user/delete"));
    }

    @Test
    @WithAnonymousUser
    void 로그인_안하면_접속_못함() throws Exception {
        MockHttpServletRequestBuilder builder = get("/user/delete");

        mockMvc.perform(builder)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/user/login"));
    }
}