package com.example.foodcloud.controller.core.order.cancel;

import com.example.foodcloud.FoodMenuFixture;
import com.example.foodcloud.GroupBuyListFixture;
import com.example.foodcloud.OrderMenuFixture;
import com.example.foodcloud.controller.advice.NotFoundExceptionAdvice;
import com.example.foodcloud.controller.core.order.OrderCancelController;
import com.example.foodcloud.controller.interceptor.LoginInterceptor;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenuRepository;
import com.example.foodcloud.domain.groupbuylist.domain.GroupBuyList;
import com.example.foodcloud.domain.groupbuylist.domain.GroupBuyListRepository;
import com.example.foodcloud.domain.ordermenu.domain.OrderMenu;
import com.example.foodcloud.domain.ordermenu.domain.OrderMenuRepository;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderCancelGetControllerTest {
    private final OrderCancelController orderCancelController;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final FoodMenuRepository foodMenuRepository;
    private final GroupBuyListRepository groupBuyListRepository;
    private final OrderMenuRepository orderMenuRepository;
    private final LoginInterceptor loginInterceptor;
    private final NotFoundExceptionAdvice notFoundExceptionAdvice;
    private MockMvc mockMvc;

    @Autowired
    public OrderCancelGetControllerTest(OrderCancelController orderCancelController, UserRepository userRepository, RestaurantRepository restaurantRepository, FoodMenuRepository foodMenuRepository, GroupBuyListRepository groupBuyListRepository, OrderMenuRepository orderMenuRepository, LoginInterceptor loginInterceptor, NotFoundExceptionAdvice notFoundExceptionAdvice) {
        this.orderCancelController = orderCancelController;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
        this.foodMenuRepository = foodMenuRepository;
        this.groupBuyListRepository = groupBuyListRepository;
        this.orderMenuRepository = orderMenuRepository;
        this.loginInterceptor = loginInterceptor;
        this.notFoundExceptionAdvice = notFoundExceptionAdvice;
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderCancelController)
                .setControllerAdvice(notFoundExceptionAdvice)
                .addInterceptors(loginInterceptor)
                .build();
    }

    @Test
    void 주문_취소_Get_정상작동() throws Exception {
        User user = new User("testUserName", "testPassword", "testPhone");
        userRepository.save(user);

        Restaurant restaurant = new Restaurant("testRestaurantName", "testLocation", "testHours", user);
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = foodMenuRepository.save(FoodMenuFixture.fixture(restaurant).build());
        foodMenuRepository.save(foodMenu);

        GroupBuyList groupBuyList = groupBuyListRepository.save(GroupBuyListFixture.fixture(user, restaurant).build());
        groupBuyListRepository.save(groupBuyList);

        OrderMenu orderMenu = orderMenuRepository.save(OrderMenuFixture.fixture(user, groupBuyList, foodMenu).build());
        orderMenuRepository.save(orderMenu);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = get("/order/cancel")
                .param("orderMenuId", String.valueOf(orderMenu.getId()))
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/order/cancel"))
                .andExpect(model().attribute("orderMenuInfo", orderMenu));
    }

    @Test
    void 주문_취소_Get_세션_없음() throws Exception {
        User user = new User("testUserName", "testPassword", "testPhone");
        userRepository.save(user);

        Restaurant restaurant = new Restaurant("testRestaurantName", "testLocation", "testHours", user);
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = foodMenuRepository.save(FoodMenuFixture.fixture(restaurant).build());
        foodMenuRepository.save(foodMenu);

        GroupBuyList groupBuyList = groupBuyListRepository.save(GroupBuyListFixture.fixture(user, restaurant).build());
        groupBuyListRepository.save(groupBuyList);

        OrderMenu orderMenu = orderMenuRepository.save(OrderMenuFixture.fixture(user, groupBuyList, foodMenu).build());
        orderMenuRepository.save(orderMenu);

        MockHttpServletRequestBuilder builder = get("/order/cancel")
                .param("orderMenuId", String.valueOf(orderMenu.getId()));

        mockMvc.perform(builder)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/login"));
    }

    @Test
    void 주문_취소_Get_오더메뉴_고유번호_다름() throws Exception {
        User user = new User("testUserName", "testPassword", "testPhone");
        userRepository.save(user);

        Restaurant restaurant = new Restaurant("testRestaurantName", "testLocation", "testHours", user);
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = foodMenuRepository.save(FoodMenuFixture.fixture(restaurant).build());
        foodMenuRepository.save(foodMenu);

        GroupBuyList groupBuyList = groupBuyListRepository.save(GroupBuyListFixture.fixture(user, restaurant).build());
        groupBuyListRepository.save(groupBuyList);

        OrderMenu orderMenu = orderMenuRepository.save(OrderMenuFixture.fixture(user, groupBuyList, foodMenu).build());
        orderMenuRepository.save(orderMenu);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = get("/order/cancel")
                .param("orderMenuId", String.valueOf(orderMenu.getId() + 1L))
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/error/error-page"))
                .andExpect(model().attribute("errorMsg", KoreanErrorCode.ORDER_MENU_NOT_FOUND));
    }
}