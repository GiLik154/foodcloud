package com.example.foodcloud.controller.core.restaurant.restaurant;


import com.example.foodcloud.controller.advice.ParamValidateAdvice;
import com.example.foodcloud.controller.core.restaurant.restaurant.RestaurantUpdateController;
import com.example.foodcloud.controller.interceptor.LoginInterceptor;
import com.example.foodcloud.domain.restaurant.domain.Restaurant;
import com.example.foodcloud.domain.restaurant.domain.RestaurantRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RestaurantUpdateGetControllerTest {
    private final RestaurantUpdateController restaurantUpdateController;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private final LoginInterceptor loginInterceptor;
    private final ParamValidateAdvice paramValidateAdvice;
    private MockMvc mockMvc;

    @Autowired
    public RestaurantUpdateGetControllerTest(RestaurantUpdateController restaurantUpdateController, RestaurantRepository restaurantRepository, UserRepository userRepository, LoginInterceptor loginInterceptor, ParamValidateAdvice paramValidateAdvice) {
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
    void 식당_수정_Get_정상작동() throws Exception {
        User user = new User("testUserName", "testPassword", "testPhone");
        userRepository.save(user);

        Restaurant restaurant = new Restaurant("testName", "testLocation", "testHours", user);
        restaurantRepository.save(restaurant);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = get("/restaurant/update")
                .param("restaurantId", String.valueOf(restaurant.getId()))
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/restaurant/update"))
                .andExpect(model().attribute("restaurantInfo", restaurant));
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
}