package com.example.foodcloud.controller.core.order;

import com.example.foodcloud.controller.advice.NotFoundExceptionAdvice;
import com.example.foodcloud.controller.advice.UserExceptionAdvice;
import com.example.foodcloud.controller.interceptor.LoginInterceptor;
import com.example.foodcloud.domain.bank.domain.BankAccount;
import com.example.foodcloud.domain.bank.domain.BankAccountRepository;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class NewOrderControllerTest {
    private final NewOrderController newOrderController;
    private final OrderMenuRepository orderMenuRepository;
    private final UserRepository userRepository;
    private final BankAccountRepository bankAccountRepository;
    private final RestaurantRepository restaurantRepository;
    private final FoodMenuRepository foodMenuRepository;
    private final OrderMainRepository orderMainRepository;
    private final LoginInterceptor loginInterceptor;
    private final UserExceptionAdvice userExceptionAdvice;
    private final NotFoundExceptionAdvice notFoundExceptionAdvice;
    private MockMvc mockMvc;

    @Autowired
    public NewOrderControllerTest(NewOrderController newOrderController, OrderMenuRepository orderMenuRepository, UserRepository userRepository, BankAccountRepository bankAccountRepository, RestaurantRepository restaurantRepository, FoodMenuRepository foodMenuRepository, OrderMainRepository orderMainRepository, LoginInterceptor loginInterceptor, UserExceptionAdvice userExceptionAdvice, NotFoundExceptionAdvice notFoundExceptionAdvice) {
        this.newOrderController = newOrderController;
        this.orderMenuRepository = orderMenuRepository;
        this.userRepository = userRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.restaurantRepository = restaurantRepository;
        this.foodMenuRepository = foodMenuRepository;
        this.orderMainRepository = orderMainRepository;
        this.loginInterceptor = loginInterceptor;
        this.userExceptionAdvice = userExceptionAdvice;
        this.notFoundExceptionAdvice = notFoundExceptionAdvice;
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(newOrderController)
                .setControllerAdvice(userExceptionAdvice, notFoundExceptionAdvice)
                .addInterceptors(loginInterceptor)
                .build();
    }

    @Test
    void 새_주문_추가_정상작동() throws Exception {
        User user = new User("testUserName", "testPassword", "testPhone");
        userRepository.save(user);

        BankAccount bankAccount = new BankAccount("testBankName", "testBankNum", "001", user);
        bankAccountRepository.save(bankAccount);

        Restaurant restaurant = new Restaurant("testRestaurantName", "testLocation", "testHours", user);
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = new FoodMenu("testFoodMenuName", 5000, "testType", "testTemp", "testMeat", "testVegetables", restaurant);
        foodMenuRepository.save(foodMenu);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = post("/order/new")
                .param("location", "testInputLocation")
                .param("bankAccountId", String.valueOf(bankAccount.getId()))
                .param("restaurantId", String.valueOf(restaurant.getId()))
                .param("foodMenuId", String.valueOf(foodMenu.getId()))
                .param("count", String.valueOf(5))
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/order/new"));

        OrderMain orderMain = orderMainRepository.findByUserId(user.getId()).get(0);
        OrderMenu orderMenu = orderMenuRepository.findByOrderMainId(orderMain.getId()).get(0);

        assertEquals("testInputLocation", orderMain.getLocation());
        assertEquals("Payment waiting", orderMain.getResult());
        assertEquals(user, orderMain.getUser());
        assertEquals(bankAccount, orderMain.getBankAccount());
        assertEquals(restaurant, orderMain.getRestaurant());
        assertEquals("testInputLocation", orderMenu.getLocation());
        assertEquals("Payment waiting", orderMenu.getResult());
        assertEquals(user, orderMenu.getUser());
        assertEquals(bankAccount, orderMenu.getBankAccount());
        assertEquals(orderMain, orderMenu.getOrderMain());
        assertEquals(foodMenu, orderMenu.getFoodMenu());
    }

    @Test
    void 새_주문_추가_세션_없음() throws Exception {
        User user = new User("testUserName", "testPassword", "testPhone");
        userRepository.save(user);

        BankAccount bankAccount = new BankAccount("testBankName", "testBankNum", "001", user);
        bankAccountRepository.save(bankAccount);

        Restaurant restaurant = new Restaurant("testRestaurantName", "testLocation", "testHours", user);
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = new FoodMenu("testFoodMenuName", 5000, "testType", "testTemp", "testMeat", "testVegetables", restaurant);
        foodMenuRepository.save(foodMenu);

        MockHttpServletRequestBuilder builder = post("/order/new")
                .param("location", "testInputLocation")
                .param("bankAccountId", String.valueOf(bankAccount.getId()))
                .param("restaurantId", String.valueOf(restaurant.getId()))
                .param("foodMenuId", String.valueOf(foodMenu.getId()))
                .param("count", String.valueOf(5));

        mockMvc.perform(builder)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/login"));

        assertTrue(orderMainRepository.findByUserId(user.getId()).isEmpty());
    }

    @Test
    void 새_주문_추가_유저_고유번호_다름() throws Exception {
        User user = new User("testUserName", "testPassword", "testPhone");
        userRepository.save(user);

        BankAccount bankAccount = new BankAccount("testBankName", "testBankNum", "001", user);
        bankAccountRepository.save(bankAccount);

        Restaurant restaurant = new Restaurant("testRestaurantName", "testLocation", "testHours", user);
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = new FoodMenu("testFoodMenuName", 5000, "testType", "testTemp", "testMeat", "testVegetables", restaurant);
        foodMenuRepository.save(foodMenu);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId() + 1L);

        MockHttpServletRequestBuilder builder = post("/order/new")
                .param("location", "testInputLocation")
                .param("bankAccountId", String.valueOf(bankAccount.getId()))
                .param("restaurantId", String.valueOf(restaurant.getId()))
                .param("foodMenuId", String.valueOf(foodMenu.getId()))
                .param("count", String.valueOf(5))
                .session(session);

        mockMvc.perform(builder)
                .andExpect(forwardedUrl("thymeleaf/error/error-page"))
                .andExpect(model().attribute("errorMsg", KoreanErrorCode.USER_INFO_NOT_FOUND.getResult()));

        assertTrue(orderMainRepository.findByUserId(user.getId()).isEmpty());
    }

    @Test
    void 새_주문_추가_은행_고유번호_다름() throws Exception {
        User user = new User("testUserName", "testPassword", "testPhone");
        userRepository.save(user);

        BankAccount bankAccount = new BankAccount("testBankName", "testBankNum", "001", user);
        bankAccountRepository.save(bankAccount);

        Restaurant restaurant = new Restaurant("testRestaurantName", "testLocation", "testHours", user);
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = new FoodMenu("testFoodMenuName", 5000, "testType", "testTemp", "testMeat", "testVegetables", restaurant);
        foodMenuRepository.save(foodMenu);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = post("/order/new")
                .param("location", "testInputLocation")
                .param("bankAccountId", String.valueOf(bankAccount.getId() + 1L))
                .param("restaurantId", String.valueOf(restaurant.getId()))
                .param("foodMenuId", String.valueOf(foodMenu.getId()))
                .param("count", String.valueOf(5))
                .session(session);

        mockMvc.perform(builder)
                .andExpect(forwardedUrl("thymeleaf/error/error-page"))
                .andExpect(model().attribute("errorMsg", KoreanErrorCode.NOT_FOUND_BANK.getResult()));

        assertTrue(orderMainRepository.findByUserId(user.getId()).isEmpty());
    }

    @Test
    void 새_주문_추가_식당_고유번호_다름() throws Exception {
        User user = new User("testUserName", "testPassword", "testPhone");
        userRepository.save(user);

        BankAccount bankAccount = new BankAccount("testBankName", "testBankNum", "001", user);
        bankAccountRepository.save(bankAccount);

        Restaurant restaurant = new Restaurant("testRestaurantName", "testLocation", "testHours", user);
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = new FoodMenu("testFoodMenuName", 5000, "testType", "testTemp", "testMeat", "testVegetables", restaurant);
        foodMenuRepository.save(foodMenu);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = post("/order/new")
                .param("location", "testInputLocation")
                .param("bankAccountId", String.valueOf(bankAccount.getId()))
                .param("restaurantId", String.valueOf(restaurant.getId() + 1L))
                .param("foodMenuId", String.valueOf(foodMenu.getId()))
                .param("count", String.valueOf(5))
                .session(session);

        mockMvc.perform(builder)
                .andExpect(forwardedUrl("thymeleaf/error/error-page"))
                .andExpect(model().attribute("errorMsg", KoreanErrorCode.NOT_FOUND_RESTAURANT.getResult()));

        assertTrue(orderMainRepository.findByUserId(user.getId()).isEmpty());
    }

    @Test
    void 새_주문_추가_음식메뉴_고유번호_다름() throws Exception {
        User user = new User("testUserName", "testPassword", "testPhone");
        userRepository.save(user);

        BankAccount bankAccount = new BankAccount("testBankName", "testBankNum", "001", user);
        bankAccountRepository.save(bankAccount);

        Restaurant restaurant = new Restaurant("testRestaurantName", "testLocation", "testHours", user);
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = new FoodMenu("testFoodMenuName", 5000, "testType", "testTemp", "testMeat", "testVegetables", restaurant);
        foodMenuRepository.save(foodMenu);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = post("/order/new")
                .param("location", "testInputLocation")
                .param("bankAccountId", String.valueOf(bankAccount.getId()))
                .param("restaurantId", String.valueOf(restaurant.getId()))
                .param("foodMenuId", String.valueOf(foodMenu.getId() + 1L))
                .param("count", String.valueOf(5))
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/error/error-page"))
                .andExpect(model().attribute("errorMsg", KoreanErrorCode.NOT_FOUND_FOOD_MENU.getResult()));

        assertTrue(orderMainRepository.findByUserId(user.getId()).isEmpty());
        //todo 여기 오류남. 트렌젝션 오류임
    }
}