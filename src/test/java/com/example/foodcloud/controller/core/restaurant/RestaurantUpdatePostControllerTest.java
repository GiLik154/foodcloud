package com.example.foodcloud.controller.core.restaurant;


import com.example.foodcloud.controller.advice.ParamValidateAdvice;
import com.example.foodcloud.controller.advice.NotFoundExceptionAdvice;
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
class RestaurantUpdatePostControllerTest {
    private final RestaurantUpdateController restaurantUpdateController;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private final LoginInterceptor loginInterceptor;
    private final ParamValidateAdvice paramValidateAdvice;
    private MockMvc mockMvc;

    @Autowired
    public RestaurantUpdatePostControllerTest(RestaurantUpdateController restaurantUpdateController, RestaurantRepository restaurantRepository, UserRepository userRepository, LoginInterceptor loginInterceptor, ParamValidateAdvice paramValidateAdvice) {
        this.restaurantUpdateController = restaurantUpdateController;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
        this.loginInterceptor = loginInterceptor;
        this.paramValidateAdvice = paramValidateAdvice;
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(restaurantUpdateController)
                .setControllerAdvice(paramValidateAdvice)
                .addInterceptors(loginInterceptor)
                .build();
    }

    @Test
    void 식당_수정_정상작동() throws Exception {
        User user = new User("testUserName", "testPassword", "testPhone");
        userRepository.save(user);

        Restaurant restaurant = new Restaurant("testName", "testLocation", "testHours", user);
        restaurantRepository.save(restaurant);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = post("/restaurant/update")
                .param("restaurantId", String.valueOf(restaurant.getId()))
                .param("name", "updateName")
                .param("location", "updateLocation")
                .param("businessHours", "updateHours")
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/restaurant/update"))
                .andExpect(model().attribute("isUpdate", true));

        assertEquals("updateName", restaurant.getName());
        assertEquals("updateLocation", restaurant.getLocation());
        assertEquals("updateHours", restaurant.getBusinessHours());
    }

    @Test
    void 식당_수정_세션_없음() throws Exception {
        User user = new User("testUserName", "testPassword", "testPhone");
        userRepository.save(user);

        Restaurant restaurant = new Restaurant("testName", "testLocation", "testHours", user);
        restaurantRepository.save(restaurant);

        MockHttpServletRequestBuilder builder = post("/restaurant/update")
                .param("restaurantId", String.valueOf(restaurant.getId()))
                .param("name", "updateName")
                .param("location", "updateLocation")
                .param("businessHours", "updateHours");

        mockMvc.perform(builder)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/login"));

        assertNotEquals("updateName", restaurant.getName());
        assertNotEquals("updateLocation", restaurant.getLocation());
        assertNotEquals("updateHours", restaurant.getBusinessHours());
    }

    @Test
    void 식당_수정_유저_고유번호_다름() throws Exception {
        User user = new User("testUserName", "testPassword", "testPhone");
        userRepository.save(user);

        Restaurant restaurant = new Restaurant("testName", "testLocation", "testHours", user);
        restaurantRepository.save(restaurant);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId() + 1L);

        MockHttpServletRequestBuilder builder = post("/restaurant/update")
                .param("restaurantId", String.valueOf(restaurant.getId()))
                .param("name", "updateName")
                .param("location", "updateLocation")
                .param("businessHours", "updateHours")
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/restaurant/update"))
                .andExpect(model().attribute("isUpdate", false));

        assertNotEquals("updateName", restaurant.getName());
        assertNotEquals("updateLocation", restaurant.getLocation());
        assertNotEquals("updateHours", restaurant.getBusinessHours());
    }

    @Test
    void 식당_수정_식당_고유번호_다름() throws Exception {
        User user = new User("testUserName", "testPassword", "testPhone");
        userRepository.save(user);

        Restaurant restaurant = new Restaurant("testName", "testLocation", "testHours", user);
        restaurantRepository.save(restaurant);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = post("/restaurant/update")
                .param("restaurantId", String.valueOf(restaurant.getId() + 1L))
                .param("name", "updateName")
                .param("location", "updateLocation")
                .param("businessHours", "updateHours")
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/restaurant/update"))
                .andExpect(model().attribute("isUpdate", false));

        assertNotEquals("updateName", restaurant.getName());
        assertNotEquals("updateLocation", restaurant.getLocation());
        assertNotEquals("updateHours", restaurant.getBusinessHours());
    }

    @Test
    void 식당_수정_파라미터_null() throws Exception {
        User user = new User("testUserName", "testPassword", "testPhone");
        userRepository.save(user);

        Restaurant restaurant = new Restaurant("testName", "testLocation", "testHours", user);
        restaurantRepository.save(restaurant);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = post("/restaurant/update")
                .param("restaurantId", String.valueOf(restaurant.getId()))
                .param("name", "")
                .param("location", "")
                .param("businessHours", "")
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/error/error-page"))
                .andExpect(model().attribute("errorMsg", KoreanErrorCode.METHOD_ARGUMENT.getResult()));

        assertNotEquals("", restaurant.getName());
        assertNotEquals("", restaurant.getLocation());
        assertNotEquals("", restaurant.getBusinessHours());
    }

}