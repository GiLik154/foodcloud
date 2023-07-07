package com.example.foodcloud.controller.core.restaurant.restaurant.add;


import com.example.foodcloud.UserFixture;
import com.example.foodcloud.controller.advice.ParamValidateAdvice;
import com.example.foodcloud.controller.core.restaurant.restaurant.RestaurantAddController;
import com.example.foodcloud.controller.interceptor.LoginInterceptor;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RestaurantAddGetControllerTest {
    private final RestaurantAddController restaurantAddController;
    private final UserRepository userRepository;
    private final LoginInterceptor loginInterceptor;
    private final ParamValidateAdvice paramValidateAdvice;
    private MockMvc mockMvc;

    @Autowired
    public RestaurantAddGetControllerTest(RestaurantAddController restaurantAddController, UserRepository userRepository, LoginInterceptor loginInterceptor, ParamValidateAdvice paramValidateAdvice) {
        this.restaurantAddController = restaurantAddController;
        this.userRepository = userRepository;
        this.loginInterceptor = loginInterceptor;
        this.paramValidateAdvice = paramValidateAdvice;
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(restaurantAddController)
                .setControllerAdvice(paramValidateAdvice)
                .addInterceptors(loginInterceptor)
                .build();
    }

    @Test
    void 식당_추가_정상출력() throws Exception {
        User user = userRepository.save(UserFixture.fixture().build());
        userRepository.save(user);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = get("/restaurant/add")
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/restaurant/add"));
    }

    @Test
    void 식당_추가_세션_없음() throws Exception {
        User user = userRepository.save(UserFixture.fixture().build());
        userRepository.save(user);

        MockHttpServletRequestBuilder builder = get("/restaurant/add");

        mockMvc.perform(builder)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/login"));
    }
}