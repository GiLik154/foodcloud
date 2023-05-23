package com.example.foodcloud.controller.core.order.cancel;

import com.example.foodcloud.controller.advice.NotFoundExceptionAdvice;
import com.example.foodcloud.controller.advice.UserExceptionAdvice;
import com.example.foodcloud.controller.core.order.OrderCancelController;
import com.example.foodcloud.controller.interceptor.LoginInterceptor;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenuRepository;
import com.example.foodcloud.domain.order.join.domain.OrderJoinGroup;
import com.example.foodcloud.domain.order.join.domain.OrderJoinGroupRepository;
import com.example.foodcloud.domain.order.menu.domain.OrderMenu;
import com.example.foodcloud.domain.order.menu.domain.OrderMenuRepository;
import com.example.foodcloud.domain.payment.bank.domain.BankAccount;
import com.example.foodcloud.domain.payment.bank.domain.BankAccountRepository;
import com.example.foodcloud.domain.payment.point.domain.Point;
import com.example.foodcloud.domain.payment.point.domain.PointRepository;
import com.example.foodcloud.domain.restaurant.domain.Restaurant;
import com.example.foodcloud.domain.restaurant.domain.RestaurantRepository;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import com.example.foodcloud.enums.KoreanErrorCode;
import com.example.foodcloud.enums.OrderResult;
import com.example.foodcloud.enums.PaymentCode;
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
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderCancelPostControllerTest {
    private final OrderCancelController orderCancelController;
    private final UserRepository userRepository;
    private final BankAccountRepository bankAccountRepository;
    private final PointRepository pointRepository;
    private final RestaurantRepository restaurantRepository;
    private final FoodMenuRepository foodMenuRepository;
    private final OrderJoinGroupRepository orderJoinGroupRepository;
    private final OrderMenuRepository orderMenuRepository;
    private final LoginInterceptor loginInterceptor;
    private final UserExceptionAdvice userExceptionAdvice;
    private final NotFoundExceptionAdvice notFoundExceptionAdvice;
    private MockMvc mockMvc;

    @Autowired
    OrderCancelPostControllerTest(OrderCancelController orderCancelController, UserRepository userRepository, BankAccountRepository bankAccountRepository, PointRepository pointRepository, RestaurantRepository restaurantRepository, FoodMenuRepository foodMenuRepository, OrderJoinGroupRepository orderJoinGroupRepository, OrderMenuRepository orderMenuRepository, LoginInterceptor loginInterceptor, UserExceptionAdvice userExceptionAdvice, NotFoundExceptionAdvice notFoundExceptionAdvice) {
        this.orderCancelController = orderCancelController;
        this.userRepository = userRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.pointRepository = pointRepository;
        this.restaurantRepository = restaurantRepository;
        this.foodMenuRepository = foodMenuRepository;
        this.orderJoinGroupRepository = orderJoinGroupRepository;
        this.orderMenuRepository = orderMenuRepository;
        this.loginInterceptor = loginInterceptor;
        this.userExceptionAdvice = userExceptionAdvice;
        this.notFoundExceptionAdvice = notFoundExceptionAdvice;
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderCancelController)
                .setControllerAdvice(userExceptionAdvice, notFoundExceptionAdvice)
                .addInterceptors(loginInterceptor)
                .build();
    }

    @Test
    void 주문_취소_Post_국민_정상작동() throws Exception {
        User user = new User("testUserName", "testPassword", "testPhone");
        userRepository.save(user);

        BankAccount kbBank = new BankAccount(user, "test", "test", PaymentCode.KB);
        BankAccount nhBank = new BankAccount(user, "test", "test", PaymentCode.NH);
        BankAccount shBank = new BankAccount(user, "test", "test", PaymentCode.SHIN_HAN);
        bankAccountRepository.save(kbBank);
        bankAccountRepository.save(nhBank);
        bankAccountRepository.save(shBank);

        Restaurant restaurant = new Restaurant("testRestaurantName", "testLocation", "testHours", user);
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = new FoodMenu("test", 5000, Temperature.COLD, FoodTypes.ADE, MeatTypes.CHICKEN, Vegetables.FEW, restaurant);
        foodMenuRepository.save(foodMenu);

        OrderJoinGroup orderJoinGroup = new OrderJoinGroup("test", "test", user, restaurant);
        orderJoinGroupRepository.save(orderJoinGroup);

        OrderMenu orderMenu = new OrderMenu("test", 5, "test", user, foodMenu, orderJoinGroup);
        orderMenu.completeOrderWithPayment(kbBank);
        orderMenuRepository.save(orderMenu);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = post("/order/cancel")
                .param("orderMenuId", String.valueOf(orderMenu.getId()))
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/order/cancel-check"))
                .andExpect(model().attribute("cancelMsg", "25000 price KB Bank refund succeed"));

        assertEquals(OrderResult.CANCELED, orderMenu.getResult());
    }

    @Test
    void 주문_취소_Post_농협_정상작동() throws Exception {
        User user = new User("testUserName", "testPassword", "testPhone");
        userRepository.save(user);

        BankAccount kbBank = new BankAccount(user, "test", "test", PaymentCode.KB);
        BankAccount nhBank = new BankAccount(user, "test", "test", PaymentCode.NH);
        BankAccount shBank = new BankAccount(user, "test", "test", PaymentCode.SHIN_HAN);
        bankAccountRepository.save(kbBank);
        bankAccountRepository.save(nhBank);
        bankAccountRepository.save(shBank);

        Restaurant restaurant = new Restaurant("testRestaurantName", "testLocation", "testHours", user);
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = new FoodMenu("test", 5000, Temperature.COLD, FoodTypes.ADE, MeatTypes.CHICKEN, Vegetables.FEW, restaurant);
        foodMenuRepository.save(foodMenu);

        OrderJoinGroup orderJoinGroup = new OrderJoinGroup("test", "test", user, restaurant);
        orderJoinGroupRepository.save(orderJoinGroup);

        OrderMenu orderMenu = new OrderMenu("test", 5, "test", user, foodMenu, orderJoinGroup);
        orderMenu.completeOrderWithPayment(nhBank);
        orderMenuRepository.save(orderMenu);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = post("/order/cancel")
                .param("orderMenuId", String.valueOf(orderMenu.getId()))
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/order/cancel-check"))
                .andExpect(model().attribute("cancelMsg", "25000 price NH bank refund succeed"));

        assertEquals(OrderResult.CANCELED, orderMenu.getResult());
    }

    @Test
    void 주문_취소_Post_신한_정상작동() throws Exception {
        User user = new User("testUserName", "testPassword", "testPhone");
        userRepository.save(user);

        BankAccount kbBank = new BankAccount(user, "test", "test", PaymentCode.KB);
        BankAccount nhBank = new BankAccount(user, "test", "test", PaymentCode.NH);
        BankAccount shBank = new BankAccount(user, "test", "test", PaymentCode.SHIN_HAN);
        bankAccountRepository.save(kbBank);
        bankAccountRepository.save(nhBank);
        bankAccountRepository.save(shBank);

        Restaurant restaurant = new Restaurant("testRestaurantName", "testLocation", "testHours", user);
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = new FoodMenu("test", 5000, Temperature.COLD, FoodTypes.ADE, MeatTypes.CHICKEN, Vegetables.FEW, restaurant);
        foodMenuRepository.save(foodMenu);

        OrderJoinGroup orderJoinGroup = new OrderJoinGroup("test", "test", user, restaurant);
        orderJoinGroupRepository.save(orderJoinGroup);

        OrderMenu orderMenu = new OrderMenu("test", 5, "test", user, foodMenu, orderJoinGroup);
        orderMenu.completeOrderWithPayment(shBank);
        orderMenuRepository.save(orderMenu);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = post("/order/cancel")
                .param("orderMenuId", String.valueOf(orderMenu.getId()))
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/order/cancel-check"))
                .andExpect(model().attribute("cancelMsg", "25000 price ShinHan bank refund succeed"));

        assertEquals(OrderResult.CANCELED, orderMenu.getResult());
    }

    @Test
    void 주문_취소_Post_포인트_정상작동() throws Exception {
        User user = new User("testUserName", "testPassword", "testPhone");
        userRepository.save(user);

        Point point = new Point(user, PaymentCode.POINT);
        pointRepository.save(point);

        Restaurant restaurant = new Restaurant("testRestaurantName", "testLocation", "testHours", user);
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = new FoodMenu("test", 5000, Temperature.COLD, FoodTypes.ADE, MeatTypes.CHICKEN, Vegetables.FEW, restaurant);
        foodMenuRepository.save(foodMenu);

        OrderJoinGroup orderJoinGroup = new OrderJoinGroup("test", "test", user, restaurant);
        orderJoinGroupRepository.save(orderJoinGroup);

        OrderMenu orderMenu = new OrderMenu("test", 5, "test", user, foodMenu, orderJoinGroup);
        orderMenu.completeOrderWithPayment(point);
        orderMenuRepository.save(orderMenu);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = post("/order/cancel")
                .param("orderMenuId", String.valueOf(orderMenu.getId()))
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/order/cancel-check"))
                .andExpect(model().attribute("cancelMsg", "25000 price Point refund succeed"));

        assertEquals(OrderResult.CANCELED, orderMenu.getResult());
    }

    @Test
    void 주문_취소_Post_계좌_고유번호_다름() throws Exception {
        User user = new User("testUserName", "testPassword", "testPhone");
        userRepository.save(user);

        BankAccount kbBank = new BankAccount(user, "test", "test", PaymentCode.KB);
        BankAccount nhBank = new BankAccount(user, "test", "test", PaymentCode.NH);
        BankAccount shBank = new BankAccount(user, "test", "test", PaymentCode.SHIN_HAN);
        bankAccountRepository.save(kbBank);
        bankAccountRepository.save(nhBank);
        bankAccountRepository.save(shBank);

        Restaurant restaurant = new Restaurant("testRestaurantName", "testLocation", "testHours", user);
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = new FoodMenu("test", 5000, Temperature.COLD, FoodTypes.ADE, MeatTypes.CHICKEN, Vegetables.FEW, restaurant);
        foodMenuRepository.save(foodMenu);

        OrderJoinGroup orderJoinGroup = new OrderJoinGroup("test", "test", user, restaurant);
        orderJoinGroupRepository.save(orderJoinGroup);

        OrderMenu orderMenu = new OrderMenu("test", 5, "test", user, foodMenu, orderJoinGroup);
        orderMenu.completeOrderWithPayment(shBank);
        orderMenuRepository.save(orderMenu);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = post("/order/cancel")
                .param("orderMenuId", String.valueOf(orderMenu.getId() + 1L))
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/error/error-page"))
                .andExpect(model().attribute("errorMsg", KoreanErrorCode.ORDER_MENU_NOT_FOUND));

        assertNotEquals(OrderResult.CANCELED, orderMenu.getResult());
    }
}