package com.example.foodcloud.controller.core.restaurant.restaurant.add;


import com.example.foodcloud.UserFixture;
import com.example.foodcloud.controller.advice.ParamValidateAdvice;
import com.example.foodcloud.controller.advice.UserExceptionAdvice;
import com.example.foodcloud.controller.core.restaurant.restaurant.RestaurantAddController;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RestaurantAddPostControllerTest {
    private final RestaurantAddController restaurantAddController;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private final ParamValidateAdvice paramValidateAdvice;
    private final UserExceptionAdvice userExceptionAdvice;
    private MockMvc mockMvc;

    @Autowired
    public RestaurantAddPostControllerTest(RestaurantAddController restaurantAddController, RestaurantRepository restaurantRepository, UserRepository userRepository, ParamValidateAdvice paramValidateAdvice, UserExceptionAdvice userExceptionAdvice) {
        this.restaurantAddController = restaurantAddController;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
        this.paramValidateAdvice = paramValidateAdvice;
        this.userExceptionAdvice = userExceptionAdvice;
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(restaurantAddController)
                .setControllerAdvice(userExceptionAdvice, paramValidateAdvice)
                .build();
    }

    @Test
    void Post_식당_추가_정상작동() throws Exception {
        User user = userRepository.save(UserFixture.fixture().build());
        userRepository.save(user);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = post("/restaurant/add")
                .param("name", "testName")
                .param("location", "testLocation")
                .param("openHours", "00:00")
                .param("closeHours", "12:00")
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/restaurant/add"));

        Restaurant restaurant = restaurantRepository.findByUserId(user.getId()).get(0);

        assertEquals("testName", restaurant.getName());
        assertEquals("testLocation", restaurant.getLocation());
        assertEquals("00:00-12:00", restaurant.getBusinessHours());
    }

    @Test
    void Post_식당_추가_세션_없음() throws Exception {
        User user = userRepository.save(UserFixture.fixture().build());
        userRepository.save(user);

        MockHttpServletRequestBuilder builder = post("/restaurant/add")
                .param("name", "testName")
                .param("location", "testLocation")
                .param("openHours", "00:00")
                .param("closeHours", "12:00");

        mockMvc.perform(builder)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/login"));

        assertTrue(restaurantRepository.findByUserId(user.getId()).isEmpty());
    }

    @Test
    void Post_식당_추가_유저_고유번호_다름() throws Exception {
        User user = userRepository.save(UserFixture.fixture().build());
        userRepository.save(user);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId() +1L);

        MockHttpServletRequestBuilder builder = post("/restaurant/add")
                .param("name", "testName")
                .param("location", "testLocation")
                .param("openHours", "00:00")
                .param("closeHours", "12:00")
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/error/error-page"))
                .andExpect(model().attribute("errorMsg", KoreanErrorCode.USER_INFO_NOT_FOUND.getResult()));

        assertTrue(restaurantRepository.findByUserId(user.getId()).isEmpty());
    }

    @Test
    void Post_식당_추가_파라미터_null() throws Exception {
        User user = userRepository.save(UserFixture.fixture().build());
        userRepository.save(user);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = post("/restaurant/add")
                .param("name", "")
                .param("location", "")
                .param("openHours", "00:00")
                .param("closeHours", "12:00")
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/error/error-page"))
                .andExpect(model().attribute("errorMsg", KoreanErrorCode.METHOD_ARGUMENT.getResult()));

        assertTrue(restaurantRepository.findByUserId(user.getId()).isEmpty());
    }
}