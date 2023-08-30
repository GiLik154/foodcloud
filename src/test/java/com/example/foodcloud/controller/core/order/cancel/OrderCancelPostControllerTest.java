package com.example.foodcloud.controller.core.order.cancel;

import com.example.foodcloud.*;
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
import com.example.foodcloud.enums.PaymentCode;
import com.example.foodcloud.security.login.UserDetail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderCancelPostControllerTest {
    private final WebApplicationContext context;
    private final UserRepository userRepository;
    private final BankAccountRepository bankAccountRepository;
    private final PointRepository pointRepository;
    private final RestaurantRepository restaurantRepository;
    private final FoodMenuRepository foodMenuRepository;
    private final GroupBuyListRepository groupBuyListRepository;
    private final OrderMenuRepository orderMenuRepository;
    private MockMvc mockMvc;

    @Autowired
    OrderCancelPostControllerTest(WebApplicationContext context, UserRepository userRepository, BankAccountRepository bankAccountRepository, PointRepository pointRepository, RestaurantRepository restaurantRepository, FoodMenuRepository foodMenuRepository, GroupBuyListRepository groupBuyListRepository, OrderMenuRepository orderMenuRepository) {
        this.context = context;
        this.userRepository = userRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.pointRepository = pointRepository;
        this.restaurantRepository = restaurantRepository;
        this.foodMenuRepository = foodMenuRepository;
        this.groupBuyListRepository = groupBuyListRepository;
        this.orderMenuRepository = orderMenuRepository;
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    void 주문_취소_Post_국민_정상작동() throws Exception {
        User user = userRepository.save(UserFixture.fixture().build());

        BankAccount kbBank = new BankAccount(user, "test", "test", PaymentCode.KB);
        BankAccount nhBank = new BankAccount(user, "test", "test", PaymentCode.NH);
        BankAccount shBank = new BankAccount(user, "test", "test", PaymentCode.SHIN_HAN);
        bankAccountRepository.save(kbBank);
        bankAccountRepository.save(nhBank);
        bankAccountRepository.save(shBank);

        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());
        FoodMenu foodMenu = foodMenuRepository.save(FoodMenuFixture.fixture(restaurant).build());
        GroupBuyList groupBuyList = groupBuyListRepository.save(GroupBuyListFixture.fixture(user, restaurant).build());
        OrderMenu orderMenu = orderMenuRepository.save(OrderMenuFixture.fixture(user, groupBuyList, foodMenu).build());
        orderMenu.completeOrderWithPayment(kbBank);

        UserDetail userDetail = new UserDetail(user.getName(), user.getPassword(), user.getUserGrade(), user.getId());
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        MockHttpServletRequestBuilder builder = put("/order/cancel/" + orderMenu.getId())
                .with(csrf());

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(view().name("thymeleaf/order/cancel-check"))
                .andExpect(model().attribute("cancelMsg", "50000 price KB Bank refund succeed"));

        assertEquals(OrderResult.CANCELED, orderMenu.getResult());
    }

    @Test
    void 주문_취소_Post_농협_정상작동() throws Exception {
        User user = userRepository.save(UserFixture.fixture().build());

        BankAccount kbBank = new BankAccount(user, "test", "test", PaymentCode.KB);
        BankAccount nhBank = new BankAccount(user, "test", "test", PaymentCode.NH);
        BankAccount shBank = new BankAccount(user, "test", "test", PaymentCode.SHIN_HAN);
        bankAccountRepository.save(kbBank);
        bankAccountRepository.save(nhBank);
        bankAccountRepository.save(shBank);

        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());
        FoodMenu foodMenu = foodMenuRepository.save(FoodMenuFixture.fixture(restaurant).build());
        GroupBuyList groupBuyList = groupBuyListRepository.save(GroupBuyListFixture.fixture(user, restaurant).build());
        OrderMenu orderMenu = orderMenuRepository.save(OrderMenuFixture.fixture(user, groupBuyList, foodMenu).build());
        orderMenu.completeOrderWithPayment(nhBank);

        UserDetail userDetail = new UserDetail(user.getName(), user.getPassword(), user.getUserGrade(), user.getId());
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        MockHttpServletRequestBuilder builder = put("/order/cancel/" + orderMenu.getId())
                .with(csrf());

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(view().name("thymeleaf/order/cancel-check"))
                .andExpect(model().attribute("cancelMsg", "50000 price NH bank refund succeed"));

        assertEquals(OrderResult.CANCELED, orderMenu.getResult());
    }

    @Test
    void 주문_취소_Post_신한_정상작동() throws Exception {
        User user = userRepository.save(UserFixture.fixture().build());

        BankAccount kbBank = new BankAccount(user, "test", "test", PaymentCode.KB);
        BankAccount nhBank = new BankAccount(user, "test", "test", PaymentCode.NH);
        BankAccount shBank = new BankAccount(user, "test", "test", PaymentCode.SHIN_HAN);
        bankAccountRepository.save(kbBank);
        bankAccountRepository.save(nhBank);
        bankAccountRepository.save(shBank);

        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());
        FoodMenu foodMenu = foodMenuRepository.save(FoodMenuFixture.fixture(restaurant).build());
        GroupBuyList groupBuyList = groupBuyListRepository.save(GroupBuyListFixture.fixture(user, restaurant).build());
        OrderMenu orderMenu = orderMenuRepository.save(OrderMenuFixture.fixture(user, groupBuyList, foodMenu).build());
        orderMenu.completeOrderWithPayment(shBank);

        UserDetail userDetail = new UserDetail(user.getName(), user.getPassword(), user.getUserGrade(), user.getId());
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        MockHttpServletRequestBuilder builder = put("/order/cancel/" + orderMenu.getId())
                .with(csrf());

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(view().name("thymeleaf/order/cancel-check"))
                .andExpect(model().attribute("cancelMsg", "50000 price ShinHan bank refund succeed"));

        assertEquals(OrderResult.CANCELED, orderMenu.getResult());
    }

    @Test
    void 주문_취소_Post_포인트_정상작동() throws Exception {
        User user = userRepository.save(UserFixture.fixture().build());

        Point point = new Point(user);
        pointRepository.save(point);

        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());
        FoodMenu foodMenu = foodMenuRepository.save(FoodMenuFixture.fixture(restaurant).build());
        GroupBuyList groupBuyList = groupBuyListRepository.save(GroupBuyListFixture.fixture(user, restaurant).build());
        OrderMenu orderMenu = orderMenuRepository.save(OrderMenuFixture.fixture(user, groupBuyList, foodMenu).build());
        orderMenu.completeOrderWithPayment(point);

        UserDetail userDetail = new UserDetail(user.getName(), user.getPassword(), user.getUserGrade(), user.getId());
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        MockHttpServletRequestBuilder builder = put("/order/cancel/" + orderMenu.getId())
                .with(csrf());

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(view().name("thymeleaf/order/cancel-check"))
                .andExpect(model().attribute("cancelMsg", "50000 price Point refund succeed"));

        assertEquals(OrderResult.CANCELED, orderMenu.getResult());
    }

    @Test
    void 주문_취소_Post_계좌_고유번호_다름() throws Exception {
        User user = userRepository.save(UserFixture.fixture().build());

        BankAccount kbBank = new BankAccount(user, "test", "test", PaymentCode.KB);
        BankAccount nhBank = new BankAccount(user, "test", "test", PaymentCode.NH);
        BankAccount shBank = new BankAccount(user, "test", "test", PaymentCode.SHIN_HAN);
        bankAccountRepository.save(kbBank);
        bankAccountRepository.save(nhBank);
        bankAccountRepository.save(shBank);

        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());
        FoodMenu foodMenu = foodMenuRepository.save(FoodMenuFixture.fixture(restaurant).build());
        GroupBuyList groupBuyList = groupBuyListRepository.save(GroupBuyListFixture.fixture(user, restaurant).build());
        OrderMenu orderMenu = orderMenuRepository.save(OrderMenuFixture.fixture(user, groupBuyList, foodMenu).build());
        orderMenu.completeOrderWithPayment(shBank);

        UserDetail userDetail = new UserDetail(user.getName(), user.getPassword(), user.getUserGrade(), user.getId());
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        MockHttpServletRequestBuilder builder = put("/order/cancel/" + orderMenu.getId() + 1L)
                .with(csrf());

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(view().name("thymeleaf/error/error-page"))
                .andExpect(model().attribute("errorMsg", KoreanErrorCode.ORDER_MENU_NOT_FOUND.getResult()));

        assertNotEquals(OrderResult.CANCELED, orderMenu.getResult());
    }

    @Test
    @WithAnonymousUser
    void 로그인_안하면_접속_못함() throws Exception {
        MockHttpServletRequestBuilder builder = put("/order/cancel/" + 1L)
                .with(csrf());

        mockMvc.perform(builder)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/user/login"));
    }
}