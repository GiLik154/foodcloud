package com.example.foodcloud.controller.core.order;

import com.example.foodcloud.controller.advice.NotFoundExceptionAdvice;
import com.example.foodcloud.controller.advice.UserExceptionAdvice;
import com.example.foodcloud.controller.core.foodmenu.dto.FoodMenuUpdateControllerDto;
import com.example.foodcloud.controller.core.order.dto.JoinOrderControllerDto;
import com.example.foodcloud.controller.interceptor.LoginInterceptor;
import com.example.foodcloud.domain.foodmenu.service.update.dto.FoodMenuUpdateServiceDto;
import com.example.foodcloud.domain.order.menu.service.add.dto.OrderMenuAddServiceDto;
import com.example.foodcloud.domain.payment.bank.domain.BankAccountRepository;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenuRepository;
import com.example.foodcloud.domain.order.main.domain.OrderMain;
import com.example.foodcloud.domain.order.main.domain.OrderMainRepository;
import com.example.foodcloud.domain.order.menu.domain.OrderMenu;
import com.example.foodcloud.domain.order.menu.domain.OrderMenuRepository;
import com.example.foodcloud.domain.restaurant.domain.Restaurant;
import com.example.foodcloud.domain.restaurant.domain.RestaurantRepository;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import com.example.foodcloud.domain.user.service.join.dto.UserJoinServiceDto;
import com.example.foodcloud.enums.KoreanErrorCode;
import com.example.foodcloud.enums.OrderResult;
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
import com.example.foodcloud.enums.foodmenu.FoodTypes;
import com.example.foodcloud.enums.foodmenu.MeatTypes;
import com.example.foodcloud.enums.foodmenu.Temperature;
import com.example.foodcloud.enums.foodmenu.Vegetables;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class JoinOrderPostControllerTest {
    private final JoinOrderController joinOrderController;
    private final OrderMenuRepository orderMenuRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final FoodMenuRepository foodMenuRepository;
    private final OrderMainRepository orderMainRepository;
    private final LoginInterceptor loginInterceptor;
    private final UserExceptionAdvice userExceptionAdvice;
    private final NotFoundExceptionAdvice notFoundExceptionAdvice;
    private MockMvc mockMvc;

    @Autowired
    public JoinOrderPostControllerTest(JoinOrderController joinOrderController, OrderMenuRepository orderMenuRepository, UserRepository userRepository, BankAccountRepository bankAccountRepository, RestaurantRepository restaurantRepository, FoodMenuRepository foodMenuRepository, OrderMainRepository orderMainRepository, LoginInterceptor loginInterceptor, UserExceptionAdvice userExceptionAdvice, NotFoundExceptionAdvice notFoundExceptionAdvice) {
        this.joinOrderController = joinOrderController;
        this.orderMenuRepository = orderMenuRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
        this.foodMenuRepository = foodMenuRepository;
        this.orderMainRepository = orderMainRepository;
        this.loginInterceptor = loginInterceptor;
        this.userExceptionAdvice = userExceptionAdvice;
        this.notFoundExceptionAdvice = notFoundExceptionAdvice;
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(joinOrderController)
                .setControllerAdvice(userExceptionAdvice, notFoundExceptionAdvice)
                .addInterceptors(loginInterceptor)
                .build();
    }

    @Test
    void Post_주문_추가_정상작동() throws Exception {
        User user = new User("testUserName", "testPassword", "testPhone");
        userRepository.save(user);

        Restaurant restaurant = new Restaurant("testRestaurantName", "testLocation", "testHours", user);
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = new FoodMenu("test", 5000, Temperature.COLD, FoodTypes.ADE, MeatTypes.CHICKEN, Vegetables.FEW, restaurant);
        foodMenuRepository.save(foodMenu);

        OrderMain orderMain = new OrderMain("test", "test", user, restaurant);
        orderMainRepository.save(orderMain);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = post("/order/join")
                .param("location", "testInputLocation")
                .param("count", String.valueOf(5))
                .param("foodMenuId", String.valueOf(foodMenu.getId()))
                .param("orderMainId", String.valueOf(orderMain.getId()))
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/order/join"));

        OrderMenu orderMenu = orderMenuRepository.findByOrderMainId(orderMain.getId()).get(0);

        assertEquals("testInputLocation", orderMenu.getLocation());
        assertEquals(OrderResult.PAYMENT_WAITING, orderMenu.getResult());
        assertEquals(user, orderMenu.getUser());
        assertEquals(orderMain, orderMenu.getOrderMain());
        assertEquals(foodMenu, orderMenu.getFoodMenu());
    }

    @Test
    void Post_주문_추가_세션_없음() throws Exception {
        User user = new User("testUserName", "testPassword", "testPhone");
        userRepository.save(user);

        Restaurant restaurant = new Restaurant("testRestaurantName", "testLocation", "testHours", user);
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = new FoodMenu("test", 5000, Temperature.COLD, FoodTypes.ADE, MeatTypes.CHICKEN, Vegetables.FEW, restaurant);
        foodMenuRepository.save(foodMenu);

        OrderMain orderMain = new OrderMain("test", "test", user, restaurant);
        orderMainRepository.save(orderMain);

        MockHttpServletRequestBuilder builder = post("/order/join")
                .param("location", "testInputLocation")
                .param("count", String.valueOf(5))
                .param("foodMenuId", String.valueOf(foodMenu.getId()))
                .param("orderMainId", String.valueOf(orderMain.getId()));

        mockMvc.perform(builder)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/login"));

        assertTrue(orderMenuRepository.findByOrderMainId(orderMain.getId()).isEmpty());
    }

    @Test
    void Post_주문_추가_유저_고유번호_다름() throws Exception {
        User user = new User("testUserName", "testPassword", "testPhone");
        userRepository.save(user);

        Restaurant restaurant = new Restaurant("testRestaurantName", "testLocation", "testHours", user);
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = new FoodMenu("test", 5000, Temperature.COLD, FoodTypes.ADE, MeatTypes.CHICKEN, Vegetables.FEW, restaurant);
        foodMenuRepository.save(foodMenu);

        OrderMain orderMain = new OrderMain("test", "test", user, restaurant);
        orderMainRepository.save(orderMain);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId() + 1L);

        MockHttpServletRequestBuilder builder = post("/order/join")
                .param("location", "testInputLocation")
                .param("count", String.valueOf(5))
                .param("foodMenuId", String.valueOf(foodMenu.getId()))
                .param("orderMainId", String.valueOf(orderMain.getId()))
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/error/error-page"))
                .andExpect(model().attribute("errorMsg", KoreanErrorCode.USER_INFO_NOT_FOUND.getResult()));

        assertTrue(orderMenuRepository.findByOrderMainId(orderMain.getId()).isEmpty());
    }

    @Test
    void Post_주문_추가_메뉴_고유번호_다름() throws Exception {
        User user = new User("testUserName", "testPassword", "testPhone");
        userRepository.save(user);

        Restaurant restaurant = new Restaurant("testRestaurantName", "testLocation", "testHours", user);
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = new FoodMenu("test", 5000, Temperature.COLD, FoodTypes.ADE, MeatTypes.CHICKEN, Vegetables.FEW, restaurant);
        foodMenuRepository.save(foodMenu);

        OrderMain orderMain = new OrderMain("test", "test", user, restaurant);
        orderMainRepository.save(orderMain);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = post("/order/join")
                .param("location", "testInputLocation")
                .param("count", String.valueOf(5))
                .param("foodMenuId", String.valueOf(foodMenu.getId() + 1L))
                .param("orderMainId", String.valueOf(orderMain.getId()))
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/error/error-page"))
                .andExpect(model().attribute("errorMsg", KoreanErrorCode.FOOD_MENU_NOT_FOUND.getResult()));

        assertTrue(orderMenuRepository.findByOrderMainId(orderMain.getId()).isEmpty());
    }

    @Test
    void Post_주문_추가_오더메인_고유번호_다름() throws Exception {
        User user = new User("testUserName", "testPassword", "testPhone");
        userRepository.save(user);

        Restaurant restaurant = new Restaurant("testRestaurantName", "testLocation", "testHours", user);
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = new FoodMenu("test", 5000, Temperature.COLD, FoodTypes.ADE, MeatTypes.CHICKEN, Vegetables.FEW, restaurant);
        foodMenuRepository.save(foodMenu);

        OrderMain orderMain = new OrderMain("test", "test", user, restaurant);
        orderMainRepository.save(orderMain);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = post("/order/join")
                .param("location", "testInputLocation")
                .param("count", String.valueOf(5))
                .param("foodMenuId", String.valueOf(foodMenu.getId()))
                .param("orderMainId", String.valueOf(orderMain.getId() + 1L))
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/error/error-page"))
                .andExpect(model().attribute("errorMsg", KoreanErrorCode.ORDER_MAIN_NOT_FOUND.getResult()));

        assertTrue(orderMenuRepository.findByOrderMainId(orderMain.getId()).isEmpty());
    }
}