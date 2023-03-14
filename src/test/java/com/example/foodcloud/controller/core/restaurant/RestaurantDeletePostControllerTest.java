package com.example.foodcloud.controller.core.restaurant;

import com.example.foodcloud.controller.advice.UserExceptionAdvice;
import com.example.foodcloud.controller.interceptor.LoginInterceptor;
import com.example.foodcloud.domain.restaurant.domain.Restaurant;
import com.example.foodcloud.domain.restaurant.domain.RestaurantRepository;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import com.example.foodcloud.enums.KoreanErrorCode;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RestaurantDeletePostControllerTest {
    private final RestaurantDeleteController restaurantDeleteController;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final LoginInterceptor loginInterceptor;
    private final UserExceptionAdvice userExceptionAdvice;
    private MockMvc mockMvc;

    @Autowired
    public RestaurantDeletePostControllerTest(RestaurantDeleteController restaurantDeleteController, RestaurantRepository restaurantRepository, UserRepository userRepository, PasswordEncoder bCryptPasswordEncoder, LoginInterceptor loginInterceptor, UserExceptionAdvice userExceptionAdvice) {
        this.restaurantDeleteController = restaurantDeleteController;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.loginInterceptor = loginInterceptor;
        this.userExceptionAdvice = userExceptionAdvice;
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(restaurantDeleteController)
                .setControllerAdvice(userExceptionAdvice)
                .addInterceptors(loginInterceptor)
                .build();
    }

    @Test
    void 식당_삭제_정상작동() throws Exception {
        User user = new User("testUserName", bCryptPasswordEncoder.encode("testPassword"), "testPhone");
        userRepository.save(user);

        Restaurant restaurant = new Restaurant("testName", "testLocation", "testHours", user);
        restaurantRepository.save(restaurant);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = post("/restaurant/delete")
                .param("restaurantId", String.valueOf(restaurant.getId()))
                .param("password", "testPassword")
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/restaurant/delete"))
                .andExpect(model().attribute("isDelete", true));

        assertTrue(restaurantRepository.findByUserId(user.getId()).isEmpty());
    }

    @Test
    void 식당_삭제_세션없음() throws Exception {
        User user = new User("testUserName", bCryptPasswordEncoder.encode("testPassword"), "testPhone");
        userRepository.save(user);

        Restaurant restaurant = new Restaurant("testName", "testLocation", "testHours", user);
        restaurantRepository.save(restaurant);

        MockHttpServletRequestBuilder builder = post("/restaurant/delete")
                .param("restaurantId", String.valueOf(restaurant.getId()))
                .param("password", "testPassword");

        mockMvc.perform(builder)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/login"));

        assertFalse(restaurantRepository.findByUserId(user.getId()).isEmpty());
    }

    @Test
    void 식당_삭제_유저_고유번호_다름() throws Exception {
        User user = new User("testUserName", bCryptPasswordEncoder.encode("testPassword"), "testPhone");
        userRepository.save(user);

        Restaurant restaurant = new Restaurant("testName", "testLocation", "testHours", user);
        restaurantRepository.save(restaurant);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId() + 1L);

        MockHttpServletRequestBuilder builder = post("/restaurant/delete")
                .param("restaurantId", String.valueOf(restaurant.getId()))
                .param("password", "testPassword")
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/error/error-page"))
                .andExpect(model().attribute("errorMsg", KoreanErrorCode.USER_INFO_NOT_FOUND.getResult()));

        assertFalse(restaurantRepository.findByUserId(user.getId()).isEmpty());
    }

    @Test
    void 식당_삭제_유저_비밀번호_다름() throws Exception {
        User user = new User("testUserName", bCryptPasswordEncoder.encode("testPassword"), "testPhone");
        userRepository.save(user);

        Restaurant restaurant = new Restaurant("testName", "testLocation", "testHours", user);
        restaurantRepository.save(restaurant);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = post("/restaurant/delete")
                .param("restaurantId", String.valueOf(restaurant.getId()))
                .param("password", "wrongPassword")
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/error/error-page"))
                .andExpect(model().attribute("errorMsg", KoreanErrorCode.USER_INFO_NOT_FOUND.getResult()));

        assertFalse(restaurantRepository.findByUserId(user.getId()).isEmpty());
    }

    @Test
    void 식당_삭제_식당_고유번호_다름() throws Exception {
        User user = new User("testUserName", bCryptPasswordEncoder.encode("testPassword"), "testPhone");
        userRepository.save(user);

        Restaurant restaurant = new Restaurant("testName", "testLocation", "testHours", user);
        restaurantRepository.save(restaurant);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = post("/restaurant/delete")
                .param("restaurantId", String.valueOf(restaurant.getId() + 1L))
                .param("password", "wrongPassword")
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/restaurant/delete"))
                .andExpect(model().attribute("isDelete", false));

        assertFalse(restaurantRepository.findByUserId(user.getId()).isEmpty());
    }

}