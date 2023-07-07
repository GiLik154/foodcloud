package com.example.foodcloud.controller.core.foodmenu.update;

import com.example.foodcloud.FoodMenuFixture;
import com.example.foodcloud.RestaurantFixture;
import com.example.foodcloud.UserFixture;
import com.example.foodcloud.controller.advice.NotFoundExceptionAdvice;
import com.example.foodcloud.controller.core.foodmenu.FoodMenuUpdateController;
import com.example.foodcloud.controller.interceptor.LoginInterceptor;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
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
class FoodMenuUpdateGetControllerTest {
    private final FoodMenuUpdateController foodMenuUpdateController;
    private final NotFoundExceptionAdvice notFoundExceptionAdvice;
    private final LoginInterceptor loginInterceptor;
    private final FoodMenuRepository foodMenuRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private MockMvc mockMvc;

    @Autowired
    public FoodMenuUpdateGetControllerTest(FoodMenuUpdateController foodMenuUpdateController, NotFoundExceptionAdvice notFoundExceptionAdvice, LoginInterceptor loginInterceptor, FoodMenuRepository foodMenuRepository, RestaurantRepository restaurantRepository, UserRepository userRepository) {
        this.foodMenuUpdateController = foodMenuUpdateController;
        this.notFoundExceptionAdvice = notFoundExceptionAdvice;
        this.loginInterceptor = loginInterceptor;
        this.foodMenuRepository = foodMenuRepository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(foodMenuUpdateController)
                .setControllerAdvice(notFoundExceptionAdvice)
                .addInterceptors(loginInterceptor)
                .build();
    }

    @Test
    void Get_음식_메뉴_업데이트_정상_출력() throws Exception {
        User user = userRepository.save(UserFixture.fixture().build());
        userRepository.save(user);

        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = foodMenuRepository.save(FoodMenuFixture.fixture(restaurant).build());
        foodMenuRepository.save(foodMenu);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = get("/food-menu/update/" + foodMenu.getId())
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/food-menu/update"))
                .andExpect(model().attribute("foodMenu", foodMenuRepository.findById(foodMenu.getId()).get()));
    }

    @Test
    void GGet_음식_메뉴_업데이트_세션_없음() throws Exception {
        User user = userRepository.save(UserFixture.fixture().build());
        userRepository.save(user);

        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = foodMenuRepository.save(FoodMenuFixture.fixture(restaurant).build());
        foodMenuRepository.save(foodMenu);

        MockHttpServletRequestBuilder builder = get("/food-menu/update/" + foodMenu.getId());

        mockMvc.perform(builder)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/login"));
    }

    @Test
    void Get_음식_메뉴_업데이트_고유번호_다름() throws Exception {
        User user = userRepository.save(UserFixture.fixture().build());
        userRepository.save(user);

        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = foodMenuRepository.save(FoodMenuFixture.fixture(restaurant).build());
        foodMenuRepository.save(foodMenu);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = get("/food-menu/update/" + foodMenu.getId())
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/food-menu/update"))
                .andExpect(model().attributeDoesNotExist("foodMenuInfo"));
    }
}