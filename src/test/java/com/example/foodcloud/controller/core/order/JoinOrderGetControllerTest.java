package com.example.foodcloud.controller.core.order;

import com.example.foodcloud.controller.advice.NotFoundExceptionAdvice;
import com.example.foodcloud.controller.advice.UserExceptionAdvice;
import com.example.foodcloud.controller.interceptor.LoginInterceptor;
import com.example.foodcloud.domain.payment.bank.domain.BankAccountRepository;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenuRepository;
import com.example.foodcloud.domain.order.join.domain.OrderJoinGroup;
import com.example.foodcloud.domain.order.join.domain.OrderJoinGroupRepository;
import com.example.foodcloud.domain.order.menu.domain.OrderMenuRepository;
import com.example.foodcloud.domain.restaurant.domain.Restaurant;
import com.example.foodcloud.domain.restaurant.domain.RestaurantRepository;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import com.example.foodcloud.enums.foodmenu.FoodTypes;
import com.example.foodcloud.enums.foodmenu.MeatTypes;
import com.example.foodcloud.enums.foodmenu.Temperature;
import com.example.foodcloud.enums.foodmenu.Vegetables;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class JoinOrderGetControllerTest {
    private final OrderGroupJoinController orderGroupJoinController;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final FoodMenuRepository foodMenuRepository;
    private final OrderJoinGroupRepository orderJoinGroupRepository;
    private final LoginInterceptor loginInterceptor;
    private final UserExceptionAdvice userExceptionAdvice;
    private final NotFoundExceptionAdvice notFoundExceptionAdvice;
    private MockMvc mockMvc;

    @Autowired
    public JoinOrderGetControllerTest(OrderGroupJoinController orderGroupJoinController, OrderMenuRepository orderMenuRepository, UserRepository userRepository, BankAccountRepository bankAccountRepository, RestaurantRepository restaurantRepository, FoodMenuRepository foodMenuRepository, OrderJoinGroupRepository orderJoinGroupRepository, LoginInterceptor loginInterceptor, UserExceptionAdvice userExceptionAdvice, NotFoundExceptionAdvice notFoundExceptionAdvice) {
        this.orderGroupJoinController = orderGroupJoinController;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
        this.foodMenuRepository = foodMenuRepository;
        this.orderJoinGroupRepository = orderJoinGroupRepository;
        this.loginInterceptor = loginInterceptor;
        this.userExceptionAdvice = userExceptionAdvice;
        this.notFoundExceptionAdvice = notFoundExceptionAdvice;
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderGroupJoinController)
                .setControllerAdvice(userExceptionAdvice, notFoundExceptionAdvice)
                .addInterceptors(loginInterceptor)
                .build();
    }

    @Test
    void 주문_추가_정상작동() throws Exception {
        User user = new User("testUserName", "testPassword", "testPhone");
        userRepository.save(user);

        Restaurant restaurant = new Restaurant("testRestaurantName", "testLocation", "testHours", user);
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = new FoodMenu("test", 5000, Temperature.COLD, FoodTypes.ADE, MeatTypes.CHICKEN, Vegetables.FEW, restaurant);
        foodMenuRepository.save(foodMenu);

        OrderJoinGroup orderJoinGroup = new OrderJoinGroup("test", "test", user, restaurant);
        orderJoinGroupRepository.save(orderJoinGroup);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = get("/order/join")
                .param("orderJoinGroupId", String.valueOf(orderJoinGroup.getId()))
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/order/join"))
                .andExpect(model().attribute("OrderJoinGroupInfo", orderJoinGroup));
    }

    @Test
    void 주문_추가_세션_없음() throws Exception {
        User user = new User("testUserName", "testPassword", "testPhone");
        userRepository.save(user);

        Restaurant restaurant = new Restaurant("testRestaurantName", "testLocation", "testHours", user);
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = new FoodMenu("test", 5000, Temperature.COLD, FoodTypes.ADE, MeatTypes.CHICKEN, Vegetables.FEW, restaurant);
        foodMenuRepository.save(foodMenu);

        OrderJoinGroup orderJoinGroup = new OrderJoinGroup("test", "test", user, restaurant);
        orderJoinGroupRepository.save(orderJoinGroup);

        MockHttpServletRequestBuilder builder = post("/order/join")
                .param("location", "testInputLocation")
                .param("count", String.valueOf(5))
                .param("foodMenuId", String.valueOf(foodMenu.getId()))
                .param("OrderJoinGroupId", String.valueOf(orderJoinGroup.getId()));

        mockMvc.perform(builder)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/login"));
    }
}