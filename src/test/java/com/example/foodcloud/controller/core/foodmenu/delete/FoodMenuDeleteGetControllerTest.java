package com.example.foodcloud.controller.core.foodmenu.delete;

import com.example.foodcloud.FoodMenuFixture;
import com.example.foodcloud.RestaurantFixture;
import com.example.foodcloud.controller.advice.UserExceptionAdvice;
import com.example.foodcloud.controller.core.foodmenu.FoodMenuDeleteController;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FoodMenuDeleteGetControllerTest {

    private final FoodMenuDeleteController foodMenuDeleteController;
    private final UserExceptionAdvice userExceptionAdvice;
    private final RestaurantRepository restaurantRepository;
    private final FoodMenuRepository foodMenuRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
    private MockMvc mockMvc;

    @Autowired
    public FoodMenuDeleteGetControllerTest(FoodMenuDeleteController foodMenuDeleteController, UserExceptionAdvice userExceptionAdvice, RestaurantRepository restaurantRepository, FoodMenuRepository foodMenuRepository, UserRepository userRepository, PasswordEncoder bCryptPasswordEncoder) {
        this.foodMenuDeleteController = foodMenuDeleteController;
        this.userExceptionAdvice = userExceptionAdvice;
        this.restaurantRepository = restaurantRepository;
        this.foodMenuRepository = foodMenuRepository;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(foodMenuDeleteController)
                .setControllerAdvice(userExceptionAdvice)
                .build();
    }

    @Test
    void Get_음식_메뉴_딜리트_출력_정상작동() throws Exception {
        User user = new User("test", bCryptPasswordEncoder.encode("test"), "test");
        userRepository.save(user);

        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = foodMenuRepository.save(FoodMenuFixture.fixture(restaurant).build());
        foodMenuRepository.save(foodMenu);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = get("/food-menu/delete")
                .param("foodMenuId", String.valueOf(foodMenu.getId()))
                .param("password", "test")
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/food-menu/delete"));
    }

    @Test
    void Get_음식_메뉴_딜리트_출력_세션_없음() throws Exception {
        User user = new User("test", bCryptPasswordEncoder.encode("test"), "test");
        userRepository.save(user);

        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = foodMenuRepository.save(FoodMenuFixture.fixture(restaurant).build());
        foodMenuRepository.save(foodMenu);

        MockHttpServletRequestBuilder builder = get("/food-menu/delete")
                .param("foodMenuId", String.valueOf(foodMenu.getId()))
                .param("password", "test");

        mockMvc.perform(builder)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/login"));
    }
}