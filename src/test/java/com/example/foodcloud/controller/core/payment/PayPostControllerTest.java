package com.example.foodcloud.controller.core.payment;

import com.example.foodcloud.*;
import com.example.foodcloud.controller.advice.NotFoundExceptionAdvice;
import com.example.foodcloud.controller.advice.PointExceptionAdvice;
import com.example.foodcloud.controller.advice.UserExceptionAdvice;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenuRepository;
import com.example.foodcloud.domain.groupbuylist.domain.GroupBuyList;
import com.example.foodcloud.domain.groupbuylist.domain.GroupBuyListRepository;
import com.example.foodcloud.domain.ordermenu.domain.OrderMenu;
import com.example.foodcloud.domain.ordermenu.domain.OrderMenuRepository;
import com.example.foodcloud.domain.payment.domain.BankAccount;
import com.example.foodcloud.domain.payment.domain.BankAccountRepository;
import com.example.foodcloud.domain.payment.domain.Point;
import com.example.foodcloud.domain.payment.domain.PointRepository;
import com.example.foodcloud.domain.restaurant.domain.Restaurant;
import com.example.foodcloud.domain.restaurant.domain.RestaurantRepository;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PayPostControllerTest {
    private final OrderPayController orderPayController;
    private final UserRepository userRepository;
    private final BankAccountRepository bankAccountRepository;
    private final PointRepository pointRepository;
    private final FoodMenuRepository foodMenuRepository;
    private final GroupBuyListRepository groupBuyListRepository;
    private final OrderMenuRepository orderMenuRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserExceptionAdvice userExceptionAdvice;
    private final NotFoundExceptionAdvice notFoundExceptionAdvice;
    private final PointExceptionAdvice pointExceptionAdvice;
    private MockMvc mockMvc;

    @Autowired
    public PayPostControllerTest(OrderPayController orderPayController, UserRepository userRepository, BankAccountRepository bankAccountRepository, PointRepository pointRepository, FoodMenuRepository foodMenuRepository, GroupBuyListRepository groupBuyListRepository, OrderMenuRepository orderMenuRepository, RestaurantRepository restaurantRepository, UserExceptionAdvice userExceptionAdvice, NotFoundExceptionAdvice notFoundExceptionAdvice, PointExceptionAdvice pointExceptionAdvice) {
        this.orderPayController = orderPayController;
        this.userRepository = userRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.pointRepository = pointRepository;
        this.foodMenuRepository = foodMenuRepository;
        this.groupBuyListRepository = groupBuyListRepository;
        this.orderMenuRepository = orderMenuRepository;
        this.restaurantRepository = restaurantRepository;
        this.userExceptionAdvice = userExceptionAdvice;
        this.notFoundExceptionAdvice = notFoundExceptionAdvice;
        this.pointExceptionAdvice = pointExceptionAdvice;
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderPayController)
                .setControllerAdvice(userExceptionAdvice, notFoundExceptionAdvice, pointExceptionAdvice)
                .build();
    }

    @Test
    void Post_국민_결제_완료() throws Exception {
        User user = new User("testUserName", "testPassword", "testPhone");
        userRepository.save(user);

        BankAccount bankAccount = bankAccountRepository.save(BankAccountFixture.fixture(user).build());
        bankAccountRepository.save(bankAccount);

        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = foodMenuRepository.save(FoodMenuFixture.fixture(restaurant).build());
        foodMenuRepository.save(foodMenu);

        GroupBuyList groupBuyList = groupBuyListRepository.save(GroupBuyListFixture.fixture(user, restaurant).build());
        groupBuyListRepository.save(groupBuyList);

        OrderMenu orderMenu = orderMenuRepository.save(OrderMenuFixture.fixture(user, groupBuyList, foodMenu).build());
        orderMenuRepository.save(orderMenu);
        Long orderMenuId = orderMenu.getId();

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = post("/payment/pay/"+ orderMenu.getId())
                .param("orderMenuId", String.valueOf(orderMenuId))
                .param("paymentCode", "004")
                .param("bankAccountId", String.valueOf(bankAccount.getId()))
                .param("price", String.valueOf(5000))
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/payment/pay"))
                .andExpect(model().attribute("payment", "5000 price KB Bank payment succeed"));

        assertEquals(OrderResult.RECEIVED, orderMenu.getResult());
        assertEquals(bankAccount, orderMenu.getPayment());
    }

    @Test
    void Post_농협_결제_완료() throws Exception {
        User user = new User("testUserName", "testPassword", "testPhone");
        userRepository.save(user);

        BankAccount bankAccount = bankAccountRepository.save(BankAccountFixture.fixture(user).build());
        bankAccountRepository.save(bankAccount);

        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = foodMenuRepository.save(FoodMenuFixture.fixture(restaurant).build());
        foodMenuRepository.save(foodMenu);

        GroupBuyList groupBuyList = groupBuyListRepository.save(GroupBuyListFixture.fixture(user, restaurant).build());
        groupBuyListRepository.save(groupBuyList);

        OrderMenu orderMenu = orderMenuRepository.save(OrderMenuFixture.fixture(user, groupBuyList, foodMenu).build());
        orderMenuRepository.save(orderMenu);
        Long orderMenuId = orderMenu.getId();

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = post("/payment/pay/"+ orderMenu.getId())
                .param("orderMenuId", String.valueOf(orderMenuId))
                .param("paymentCode", "011")
                .param("bankAccountId", String.valueOf(bankAccount.getId()))
                .param("price", String.valueOf(5000))
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/payment/pay"))
                .andExpect(model().attribute("payment", "5000 price NH bank payment succeed"));

        assertEquals(OrderResult.RECEIVED, orderMenu.getResult());
        assertEquals(bankAccount, orderMenu.getPayment());
    }

    @Test
    void Post_신한_결제_완료() throws Exception {
        User user = new User("testUserName", "testPassword", "testPhone");
        userRepository.save(user);

        BankAccount bankAccount = bankAccountRepository.save(BankAccountFixture.fixture(user).build());
        bankAccountRepository.save(bankAccount);

        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = foodMenuRepository.save(FoodMenuFixture.fixture(restaurant).build());
        foodMenuRepository.save(foodMenu);

        GroupBuyList groupBuyList = groupBuyListRepository.save(GroupBuyListFixture.fixture(user, restaurant).build());
        groupBuyListRepository.save(groupBuyList);

        OrderMenu orderMenu = orderMenuRepository.save(OrderMenuFixture.fixture(user, groupBuyList, foodMenu).build());
        orderMenuRepository.save(orderMenu);
        Long orderMenuId = orderMenu.getId();

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = post("/payment/pay/"+ orderMenu.getId())
                .param("paymentCode", "088")
                .param("orderMenuId", String.valueOf(orderMenuId))
                .param("bankAccountId", String.valueOf(bankAccount.getId()))
                .param("price", String.valueOf(5000))
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/payment/pay"))
                .andExpect(model().attribute("payment", "5000 price ShinHan bank payment succeed"));

        assertEquals(OrderResult.RECEIVED, orderMenu.getResult());
        assertEquals(bankAccount, orderMenu.getPayment());
    }

    @Test
    void Post_은행_고유번호_다름() throws Exception {
        User user = new User("testUserName", "testPassword", "testPhone");
        userRepository.save(user);

        BankAccount bankAccount = bankAccountRepository.save(BankAccountFixture.fixture(user).build());
        bankAccountRepository.save(bankAccount);

        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = foodMenuRepository.save(FoodMenuFixture.fixture(restaurant).build());
        foodMenuRepository.save(foodMenu);

        GroupBuyList groupBuyList = groupBuyListRepository.save(GroupBuyListFixture.fixture(user, restaurant).build());
        groupBuyListRepository.save(groupBuyList);

        OrderMenu orderMenu = orderMenuRepository.save(OrderMenuFixture.fixture(user, groupBuyList, foodMenu).build());
        orderMenuRepository.save(orderMenu);
        Long orderMenuId = orderMenu.getId();

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = post("/payment/pay/"+ orderMenu.getId())
                .param("orderMenuId", String.valueOf(orderMenuId))
                .param("paymentCode", "088")
                .param("bankAccountId", String.valueOf(bankAccount.getId() + 1L))
                .param("price", String.valueOf(5000))
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/payment/pay"))
                .andExpect(model().attribute("payment", "ShinHan bank payment fail"));

        assertEquals(OrderResult.PAYMENT_WAITING, orderMenu.getResult());
        assertNull(orderMenu.getPayment());
    }

    @Test
    void Post_은행_코드_다름() throws Exception {
        User user = new User("testUserName", "testPassword", "testPhone");
        userRepository.save(user);

        BankAccount bankAccount = bankAccountRepository.save(BankAccountFixture.fixture(user).build());
        bankAccountRepository.save(bankAccount);

        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = foodMenuRepository.save(FoodMenuFixture.fixture(restaurant).build());
        foodMenuRepository.save(foodMenu);

        GroupBuyList groupBuyList = groupBuyListRepository.save(GroupBuyListFixture.fixture(user, restaurant).build());
        groupBuyListRepository.save(groupBuyList);

        OrderMenu orderMenu = orderMenuRepository.save(OrderMenuFixture.fixture(user, groupBuyList, foodMenu).build());
        orderMenuRepository.save(orderMenu);
        Long orderMenuId = orderMenu.getId();

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = post("/payment/pay/"+ orderMenu.getId())
                .param("orderMenuId", String.valueOf(orderMenuId))
                .param("paymentCode", "099")
                .param("bankAccountId", String.valueOf(bankAccount.getId()))
                .param("price", String.valueOf(5000))
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/error/error-page"))
                .andExpect(model().attribute("errorMsg", KoreanErrorCode.BANK_NOT_FOUND));

        assertEquals(OrderResult.PAYMENT_WAITING, orderMenu.getResult());
        assertNull(orderMenu.getPayment());
    }

    @Test
    void Post_결제_세션_없음() throws Exception {
        User user = new User("testUserName", "testPassword", "testPhone");
        userRepository.save(user);

        BankAccount bankAccount = bankAccountRepository.save(BankAccountFixture.fixture(user).build());
        bankAccountRepository.save(bankAccount);

        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = foodMenuRepository.save(FoodMenuFixture.fixture(restaurant).build());
        foodMenuRepository.save(foodMenu);

        GroupBuyList groupBuyList = groupBuyListRepository.save(GroupBuyListFixture.fixture(user, restaurant).build());
        groupBuyListRepository.save(groupBuyList);

        OrderMenu orderMenu = orderMenuRepository.save(OrderMenuFixture.fixture(user, groupBuyList, foodMenu).build());
        orderMenuRepository.save(orderMenu);
        Long orderMenuId = orderMenu.getId();

        MockHttpServletRequestBuilder builder = post("/payment/pay/"+ orderMenu.getId())
                .param("orderMenuId", String.valueOf(orderMenuId))
                .param("paymentCode", "088")
                .param("bankAccountId", String.valueOf(bankAccount.getId()))
                .param("price", String.valueOf(5000));

        mockMvc.perform(builder)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/login"));

        assertEquals(OrderResult.PAYMENT_WAITING, orderMenu.getResult());
        assertNull(orderMenu.getPayment());
    }

    @Test
    void Post_신한_결제_유저_고유번호_다름() throws Exception {
        User user = new User("testUserName", "testPassword", "testPhone");
        userRepository.save(user);

        BankAccount bankAccount = bankAccountRepository.save(BankAccountFixture.fixture(user).build());
        bankAccountRepository.save(bankAccount);

        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = foodMenuRepository.save(FoodMenuFixture.fixture(restaurant).build());
        foodMenuRepository.save(foodMenu);

        GroupBuyList groupBuyList = groupBuyListRepository.save(GroupBuyListFixture.fixture(user, restaurant).build());
        groupBuyListRepository.save(groupBuyList);

        OrderMenu orderMenu = orderMenuRepository.save(OrderMenuFixture.fixture(user, groupBuyList, foodMenu).build());
        orderMenuRepository.save(orderMenu);
        Long orderMenuId = orderMenu.getId();

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId() + 1L);

        MockHttpServletRequestBuilder builder = post("/payment/pay/"+ orderMenu.getId())
                .param("orderMenuId", String.valueOf(orderMenuId))
                .param("paymentCode", "088")
                .param("bankAccountId", String.valueOf(bankAccount.getId()))
                .param("price", String.valueOf(5000))
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/payment/pay"))
                .andExpect(model().attribute("payment", "ShinHan bank payment fail"));

        assertEquals(OrderResult.PAYMENT_WAITING, orderMenu.getResult());
        assertNull(orderMenu.getPayment());
    }

    @Test
    void Post_포인트_결제_완료() throws Exception {
        User user = new User("testUserName", "testPassword", "testPhone");
        userRepository.save(user);

        Point point = new Point(user);
        point.update(6000);
        pointRepository.save(point);

        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = foodMenuRepository.save(FoodMenuFixture.fixture(restaurant).build());
        foodMenuRepository.save(foodMenu);

        GroupBuyList groupBuyList = groupBuyListRepository.save(GroupBuyListFixture.fixture(user, restaurant).build());
        groupBuyListRepository.save(groupBuyList);

        OrderMenu orderMenu = orderMenuRepository.save(OrderMenuFixture.fixture(user, groupBuyList, foodMenu).build());
        orderMenuRepository.save(orderMenu);
        Long orderMenuId = orderMenu.getId();

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = post("/payment/pay/"+ orderMenu.getId())
                .param("orderMenuId", String.valueOf(orderMenuId))
                .param("paymentCode", "000")
                .param("bankAccountId", String.valueOf(0))
                .param("price", String.valueOf(5000))
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/payment/pay"))
                .andExpect(model().attribute("payment", "5000 price Point payment succeed"));

        assertEquals(1000, point.getTotalPoint());
        assertEquals(OrderResult.RECEIVED, orderMenu.getResult());
        assertEquals(point, orderMenu.getPayment());
    }

    @Test
    void Post_포인트_결제_잔액부족() throws Exception {
        User user = new User("testUserName", "testPassword", "testPhone");
        userRepository.save(user);

        Point point = new Point(user);
        point.update(3000);
        pointRepository.save(point);

        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = foodMenuRepository.save(FoodMenuFixture.fixture(restaurant).build());
        foodMenuRepository.save(foodMenu);

        GroupBuyList groupBuyList = groupBuyListRepository.save(GroupBuyListFixture.fixture(user, restaurant).build());
        groupBuyListRepository.save(groupBuyList);

        OrderMenu orderMenu = orderMenuRepository.save(OrderMenuFixture.fixture(user, groupBuyList, foodMenu).build());
        orderMenuRepository.save(orderMenu);
        Long orderMenuId = orderMenu.getId();

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = post("/payment/pay/"+ orderMenu.getId())
                .param("orderMenuId", String.valueOf(orderMenuId))
                .param("paymentCode", "000")
                .param("bankAccountId", String.valueOf(0))
                .param("price", String.valueOf(5000))
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/error/error-page"))
                .andExpect(model().attribute("errorMsg", KoreanErrorCode.NOT_ENOUGH_POINT.getResult()));

        assertEquals(OrderResult.PAYMENT_WAITING, orderMenu.getResult());
        assertNull(orderMenu.getPayment());
    }
}