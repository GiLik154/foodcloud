package com.example.foodcloud.controller.core.restaurant.restaurant;


import com.example.foodcloud.UserFixture;
import com.example.foodcloud.domain.restaurant.domain.Restaurant;
import com.example.foodcloud.domain.restaurant.domain.RestaurantRepository;
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
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RestaurantAddPostControllerTest {
    private final WebApplicationContext context;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private MockMvc mockMvc;

    @Autowired
    public RestaurantAddPostControllerTest(WebApplicationContext context, RestaurantRepository restaurantRepository, UserRepository userRepository) {
        this.context = context;
        this.restaurantRepository = restaurantRepository;
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
    void Post_식당_추가_정상작동() throws Exception {
        User user = userRepository.save(UserFixture.fixture().build());

        UserDetail userDetail = new UserDetail(user.getName(), user.getPassword(), user.getUserGrade(), user.getId());
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        MockHttpServletRequestBuilder builder = post("/restaurant/add")
                .with(csrf())
                .param("name", "testName")
                .param("location", "testLocation")
                .param("openHours", "00:00")
                .param("closeHours", "12:00");

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(view().name("thymeleaf/restaurant/add"));

        Restaurant restaurant = restaurantRepository.findByUserId(user.getId()).get(0);

        assertEquals("testName", restaurant.getName());
        assertEquals("testLocation", restaurant.getLocation());
        assertEquals("00:00-12:00", restaurant.getBusinessHours());
    }

    @Test
    void Post_식당_추가_파라미터_null() throws Exception {
        User user = userRepository.save(UserFixture.fixture().build());

        UserDetail userDetail = new UserDetail(user.getName(), user.getPassword(), user.getUserGrade(), user.getId());
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        MockHttpServletRequestBuilder builder = post("/restaurant/add")
                .with(csrf())
                .param("name", "")
                .param("location", "")
                .param("openHours", "00:00")
                .param("closeHours", "12:00");

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(view().name("thymeleaf/error/error-page"))
                .andExpect(model().attribute("errorMsg", KoreanErrorCode.METHOD_ARGUMENT.getResult()));

        assertTrue(restaurantRepository.findByUserId(user.getId()).isEmpty());
    }

    @Test
    @WithAnonymousUser
    void 로그인을_안하면_접속이_불가능함() throws Exception {
        MockHttpServletRequestBuilder builder = post("/restaurant/add")
                .with(csrf());

        mockMvc.perform(builder)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/user/login"));
    }
}