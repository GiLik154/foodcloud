package com.example.foodcloud.controller.core.foodmenu.list;

import com.example.foodcloud.RestaurantFixture;
import com.example.foodcloud.controller.advice.UserExceptionAdvice;
import com.example.foodcloud.controller.core.foodmenu.FoodMenuListController;
import com.example.foodcloud.controller.interceptor.LoginInterceptor;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenuRepository;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FoodMenuListControllerTest {
    private final FoodMenuListController foodMenuListController;
    private final UserExceptionAdvice userExceptionAdvice;
    private final LoginInterceptor loginInterceptor;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final FoodMenuRepository foodMenuRepository;
    private MockMvc mockMvc;

    @Autowired
    public FoodMenuListControllerTest(FoodMenuListController foodMenuListController, UserExceptionAdvice userExceptionAdvice, LoginInterceptor loginInterceptor, UserRepository userRepository, RestaurantRepository restaurantRepository, FoodMenuRepository foodMenuRepository) {
        this.foodMenuListController = foodMenuListController;
        this.userExceptionAdvice = userExceptionAdvice;
        this.loginInterceptor = loginInterceptor;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
        this.foodMenuRepository = foodMenuRepository;
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(foodMenuListController)
                .setControllerAdvice(userExceptionAdvice)
                .addInterceptors(loginInterceptor)
                .build();
    }

    @Test
    void 음식메뉴_리스트_정상출력() throws Exception {
        User user = new User("testName", "testPassword", "testPhone");
        userRepository.save(user);

        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());
        restaurantRepository.save(restaurant);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = get("/food-menu/list")
                .param("restaurantId", String.valueOf(restaurant.getId()))
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/food-menu/list"))
                .andExpect(model().attribute("foodMenuList", foodMenuRepository.findByRestaurantId(restaurant.getId())));
    }

    @Test
    void 음식메뉴_리스트_세션_없음() throws Exception {
        User user = new User("testName", "testPassword", "testPhone");
        userRepository.save(user);

        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());
        restaurantRepository.save(restaurant);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = get("/food-menu/list")
                .param("restaurantId", String.valueOf(restaurant.getId()));

        mockMvc.perform(builder)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/login"));
    }
}