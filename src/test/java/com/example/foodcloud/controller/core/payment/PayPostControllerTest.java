package com.example.foodcloud.controller.core.payment;

import com.example.foodcloud.controller.advice.NotFoundExceptionAdvice;
import com.example.foodcloud.controller.advice.PointExceptionAdvice;
import com.example.foodcloud.controller.advice.UserExceptionAdvice;
import com.example.foodcloud.controller.interceptor.LoginInterceptor;
import com.example.foodcloud.domain.payment.bank.domain.BankAccount;
import com.example.foodcloud.domain.payment.bank.domain.BankAccountRepository;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenuRepository;
import com.example.foodcloud.domain.order.main.domain.OrderMain;
import com.example.foodcloud.domain.order.main.domain.OrderMainRepository;
import com.example.foodcloud.domain.order.menu.domain.OrderMenu;
import com.example.foodcloud.domain.order.menu.domain.OrderMenuRepository;
import com.example.foodcloud.domain.payment.point.domain.Point;
import com.example.foodcloud.domain.payment.point.domain.PointRepository;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PayPostControllerTest {
    private final PayController payController;
    private final UserRepository userRepository;
    private final BankAccountRepository bankAccountRepository;
    private final PointRepository pointRepository;
    private final FoodMenuRepository foodMenuRepository;
    private final OrderMainRepository orderMainRepository;
    private final OrderMenuRepository orderMenuRepository;
    private final RestaurantRepository restaurantRepository;
    private final LoginInterceptor loginInterceptor;
    private final UserExceptionAdvice userExceptionAdvice;
    private final NotFoundExceptionAdvice notFoundExceptionAdvice;
    private final PointExceptionAdvice pointExceptionAdvice;
    private MockMvc mockMvc;

    @Autowired
    public PayPostControllerTest(PayController payController, UserRepository userRepository, BankAccountRepository bankAccountRepository, PointRepository pointRepository, FoodMenuRepository foodMenuRepository, OrderMainRepository orderMainRepository, OrderMenuRepository orderMenuRepository, RestaurantRepository restaurantRepository, LoginInterceptor loginInterceptor, UserExceptionAdvice userExceptionAdvice, NotFoundExceptionAdvice notFoundExceptionAdvice, PointExceptionAdvice pointExceptionAdvice) {
        this.payController = payController;
        this.userRepository = userRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.pointRepository = pointRepository;
        this.foodMenuRepository = foodMenuRepository;
        this.orderMainRepository = orderMainRepository;
        this.orderMenuRepository = orderMenuRepository;
        this.restaurantRepository = restaurantRepository;
        this.loginInterceptor = loginInterceptor;
        this.userExceptionAdvice = userExceptionAdvice;
        this.notFoundExceptionAdvice = notFoundExceptionAdvice;
        this.pointExceptionAdvice = pointExceptionAdvice;
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(payController)
                .setControllerAdvice(userExceptionAdvice, notFoundExceptionAdvice, pointExceptionAdvice)
                .addInterceptors(loginInterceptor)
                .build();
    }

    @Test
    void Post_국민_결제_완료() throws Exception {
        User user = new User("testUserName", "testPassword", "testPhone");
        userRepository.save(user);

        BankAccount bankAccount = new BankAccount("test", "test", "011", user);
        bankAccountRepository.save(bankAccount);

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = new FoodMenu("test", 5000, "test", "test", "test", "test", restaurant);
        foodMenuRepository.save(foodMenu);

        OrderMain orderMain = new OrderMain("test", "test", user, restaurant);
        orderMainRepository.save(orderMain);

        OrderMenu orderMenu = new OrderMenu("test", 5, "test", user, foodMenu, orderMain);
        orderMenuRepository.save(orderMenu);
        Long orderMenuId = orderMenu.getId();

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = post("/payment/pay")
                .param("orderMenuId", String.valueOf(orderMenuId))
                .param("bank", "004")
                .param("bankAccountId", String.valueOf(bankAccount.getId()))
                .param("price", String.valueOf(5000))
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/payment/pay"))
                .andExpect(model().attribute("payment", "5000 price KB Bank payment succeed"));

        assertEquals("RECEIVED", orderMenu.getResult());
        assertEquals(bankAccount, orderMenu.getBankAccount());
        assertNull(orderMenu.getPoint());
    }

    @Test
    void Post_농협_결제_완료() throws Exception {
        User user = new User("testUserName", "testPassword", "testPhone");
        userRepository.save(user);

        BankAccount bankAccount = new BankAccount("test", "test", "011", user);
        bankAccountRepository.save(bankAccount);

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = new FoodMenu("test", 5000, "test", "test", "test", "test", restaurant);
        foodMenuRepository.save(foodMenu);

        OrderMain orderMain = new OrderMain("test", "test", user, restaurant);
        orderMainRepository.save(orderMain);

        OrderMenu orderMenu = new OrderMenu("test", 5, "test", user, foodMenu, orderMain);
        orderMenuRepository.save(orderMenu);
        Long orderMenuId = orderMenu.getId();

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = post("/payment/pay")
                .param("orderMenuId", String.valueOf(orderMenuId))
                .param("bank", "011")
                .param("bankAccountId", String.valueOf(bankAccount.getId()))
                .param("price", String.valueOf(5000))
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/payment/pay"))
                .andExpect(model().attribute("payment", "5000 price NH bank payment succeed"));

        assertEquals("RECEIVED", orderMenu.getResult());
        assertEquals(bankAccount, orderMenu.getBankAccount());
        assertNull(orderMenu.getPoint());
    }

    @Test
    void Post_신한_결제_완료() throws Exception {
        User user = new User("testUserName", "testPassword", "testPhone");
        userRepository.save(user);

        BankAccount bankAccount = new BankAccount("test", "test", "011", user);
        bankAccountRepository.save(bankAccount);

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = new FoodMenu("test", 5000, "test", "test", "test", "test", restaurant);
        foodMenuRepository.save(foodMenu);

        OrderMain orderMain = new OrderMain("test", "test", user, restaurant);
        orderMainRepository.save(orderMain);

        OrderMenu orderMenu = new OrderMenu("test", 5, "test", user, foodMenu, orderMain);
        orderMenuRepository.save(orderMenu);
        Long orderMenuId = orderMenu.getId();

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = post("/payment/pay")
                .param("orderMenuId", String.valueOf(orderMenuId))
                .param("bank", "088")
                .param("bankAccountId", String.valueOf(bankAccount.getId()))
                .param("price", String.valueOf(5000))
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/payment/pay"))
                .andExpect(model().attribute("payment", "5000 price ShinHan bank payment succeed"));

        assertEquals("RECEIVED", orderMenu.getResult());
        assertEquals(bankAccount, orderMenu.getBankAccount());
        assertNull(orderMenu.getPoint());
    }

    @Test
    void Post_은행_고유번호_다름() throws Exception {
        User user = new User("testUserName", "testPassword", "testPhone");
        userRepository.save(user);

        BankAccount bankAccount = new BankAccount("test", "test", "011", user);
        bankAccountRepository.save(bankAccount);

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = new FoodMenu("test", 5000, "test", "test", "test", "test", restaurant);
        foodMenuRepository.save(foodMenu);

        OrderMain orderMain = new OrderMain("test", "test", user, restaurant);
        orderMainRepository.save(orderMain);

        OrderMenu orderMenu = new OrderMenu("test", 5, "test", user, foodMenu, orderMain);
        orderMenuRepository.save(orderMenu);
        Long orderMenuId = orderMenu.getId();

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = post("/payment/pay")
                .param("orderMenuId", String.valueOf(orderMenuId))
                .param("bank", "088")
                .param("bankAccountId", String.valueOf(bankAccount.getId() + 1L))
                .param("price", String.valueOf(5000))
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/payment/pay"))
                .andExpect(model().attribute("payment", "ShinHan bank payment fail"));

        assertEquals("PAYMENT_WAITING", orderMenu.getResult());
        assertNull(orderMenu.getBankAccount());
        assertNull(orderMenu.getPoint());
    }

    @Test
    void Post_은행_코드_다름() throws Exception {
        User user = new User("testUserName", "testPassword", "testPhone");
        userRepository.save(user);

        BankAccount bankAccount = new BankAccount("test", "test", "011", user);
        bankAccountRepository.save(bankAccount);

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = new FoodMenu("test", 5000, "test", "test", "test", "test", restaurant);
        foodMenuRepository.save(foodMenu);

        OrderMain orderMain = new OrderMain("test", "test", user, restaurant);
        orderMainRepository.save(orderMain);

        OrderMenu orderMenu = new OrderMenu("test", 5, "test", user, foodMenu, orderMain);
        orderMenuRepository.save(orderMenu);
        Long orderMenuId = orderMenu.getId();

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = post("/payment/pay")
                .param("orderMenuId", String.valueOf(orderMenuId))
                .param("bank", "099")
                .param("bankAccountId", String.valueOf(bankAccount.getId()))
                .param("price", String.valueOf(5000))
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/error/error-page"))
                .andExpect(model().attribute("errorMsg", KoreanErrorCode.BANK_NOT_FOUND.getResult()));

        assertEquals("PAYMENT_WAITING", orderMenu.getResult());
        assertNull(orderMenu.getBankAccount());
        assertNull(orderMenu.getPoint());
    }

    @Test
    void Post_결제_세션_없음() throws Exception {
        User user = new User("testUserName", "testPassword", "testPhone");
        userRepository.save(user);

        BankAccount bankAccount = new BankAccount("test", "test", "011", user);
        bankAccountRepository.save(bankAccount);

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = new FoodMenu("test", 5000, "test", "test", "test", "test", restaurant);
        foodMenuRepository.save(foodMenu);

        OrderMain orderMain = new OrderMain("test", "test", user, restaurant);
        orderMainRepository.save(orderMain);

        OrderMenu orderMenu = new OrderMenu("test", 5, "test", user, foodMenu, orderMain);
        orderMenuRepository.save(orderMenu);
        Long orderMenuId = orderMenu.getId();

        MockHttpServletRequestBuilder builder = post("/payment/pay")
                .param("orderMenuId", String.valueOf(orderMenuId))
                .param("bank", "088")
                .param("bankAccountId", String.valueOf(bankAccount.getId()))
                .param("price", String.valueOf(5000));

        mockMvc.perform(builder)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/login"));

        assertEquals("PAYMENT_WAITING", orderMenu.getResult());
        assertNull(orderMenu.getBankAccount());
        assertNull(orderMenu.getPoint());
    }

    @Test
    void Post_신한_결제_유저_고유번호_다름() throws Exception {
        User user = new User("testUserName", "testPassword", "testPhone");
        userRepository.save(user);

        BankAccount bankAccount = new BankAccount("test", "test", "011", user);
        bankAccountRepository.save(bankAccount);

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = new FoodMenu("test", 5000, "test", "test", "test", "test", restaurant);
        foodMenuRepository.save(foodMenu);

        OrderMain orderMain = new OrderMain("test", "test", user, restaurant);
        orderMainRepository.save(orderMain);

        OrderMenu orderMenu = new OrderMenu("test", 5, "test", user, foodMenu, orderMain);
        orderMenuRepository.save(orderMenu);
        Long orderMenuId = orderMenu.getId();

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId() + 1L);

        MockHttpServletRequestBuilder builder = post("/payment/pay")
                .param("orderMenuId", String.valueOf(orderMenuId))
                .param("bank", "088")
                .param("bankAccountId", String.valueOf(bankAccount.getId()))
                .param("price", String.valueOf(5000))
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/payment/pay"))
                .andExpect(model().attribute("payment", "ShinHan bank payment fail"));

        assertEquals("PAYMENT_WAITING", orderMenu.getResult());
        assertNull(orderMenu.getBankAccount());
        assertNull(orderMenu.getPoint());
    }

    @Test
    void Post_포인트_결제_완료() throws Exception {
        User user = new User("testUserName", "testPassword", "testPhone");
        userRepository.save(user);

        Point point = new Point(user);
        point.updatePoint(6000);
        pointRepository.save(point);

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = new FoodMenu("test", 5000, "test", "test", "test", "test", restaurant);
        foodMenuRepository.save(foodMenu);

        OrderMain orderMain = new OrderMain("test", "test", user, restaurant);
        orderMainRepository.save(orderMain);

        OrderMenu orderMenu = new OrderMenu("test", 5, "test", user, foodMenu, orderMain);
        orderMenuRepository.save(orderMenu);
        Long orderMenuId = orderMenu.getId();

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = post("/payment/pay")
                .param("orderMenuId", String.valueOf(orderMenuId))
                .param("bank", "000")
                .param("bankAccountId", String.valueOf(0))
                .param("price", String.valueOf(5000))
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/payment/pay"))
                .andExpect(model().attribute("payment", "5000 price Point payment succeed"));

        assertEquals(1000, point.getTotalPoint());
        assertEquals("Received", orderMenu.getResult());
        assertNull(orderMenu.getBankAccount());
        assertEquals(point, orderMenu.getPoint());
    }

    @Test
    void Post_포인트_결제_잔액부족() throws Exception {
        User user = new User("testUserName", "testPassword", "testPhone");
        userRepository.save(user);

        Point point = new Point(user);
        point.updatePoint(3000);
        pointRepository.save(point);

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = new FoodMenu("test", 5000, "test", "test", "test", "test", restaurant);
        foodMenuRepository.save(foodMenu);

        OrderMain orderMain = new OrderMain("test", "test", user, restaurant);
        orderMainRepository.save(orderMain);

        OrderMenu orderMenu = new OrderMenu("test", 5, "test", user, foodMenu, orderMain);
        orderMenuRepository.save(orderMenu);
        Long orderMenuId = orderMenu.getId();

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = post("/payment/pay")
                .param("orderMenuId", String.valueOf(orderMenuId))
                .param("bank", "000")
                .param("bankAccountId", String.valueOf(0))
                .param("price", String.valueOf(5000))
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/error/error-page"))
                .andExpect(model().attribute("errorMsg", KoreanErrorCode.NOT_ENOUGH_POINT.getResult()));

        assertEquals("Payment waiting", orderMenu.getResult());
        assertNull(orderMenu.getBankAccount());
        assertNull(orderMenu.getPoint());
    }
}