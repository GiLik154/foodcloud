package com.example.foodcloud.controller.core.payment;

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
import com.example.foodcloud.security.login.UserDetail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PayGetControllerTest {
    private final OrderPayController orderPayController;
    private final UserRepository userRepository;
    private final BankAccountRepository bankAccountRepository;
    private final PointRepository pointRepository;
    private final FoodMenuRepository foodMenuRepository;
    private final GroupBuyListRepository groupBuyListRepository;
    private final OrderMenuRepository orderMenuRepository;
    private final RestaurantRepository restaurantRepository;
    private MockMvc mockMvc;

    @Autowired
    public PayGetControllerTest(OrderPayController orderPayController, UserRepository userRepository, BankAccountRepository bankAccountRepository, PointRepository pointRepository, FoodMenuRepository foodMenuRepository, GroupBuyListRepository groupBuyListRepository, OrderMenuRepository orderMenuRepository, RestaurantRepository restaurantRepository) {
        this.orderPayController = orderPayController;
        this.userRepository = userRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.pointRepository = pointRepository;
        this.foodMenuRepository = foodMenuRepository;
        this.groupBuyListRepository = groupBuyListRepository;
        this.orderMenuRepository = orderMenuRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderPayController)
                .build();
    }

    @Test
    void 결제_Get_정상작동() throws Exception {
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

        MockHttpServletRequestBuilder builder = get("/payment/pay/" + orderMenuId)
                .param("orderMenuId", String.valueOf(orderMenuId))
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/payment/pay"))
                .andExpect(model().attribute("orderMenu", orderMenu));
    }

    @Test
    void 결제_Get_Point_결제() throws Exception {
        User user = new User("testUserName", "testPassword", "testPhone");
        userRepository.save(user);

        Point point = new Point(user);
        pointRepository.save(point);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = get("/payment/pay/point")
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(point.getId()));
    }

    @Test
    void 결제_Get__결제() throws Exception {
        User user = userRepository.save(UserFixture.fixture().build());
        BankAccount bankAccount = bankAccountRepository.save(BankAccountFixture.fixture(user).build());

        UserDetail userDetail = new UserDetail(user.getName(), user.getPassword(), user.getUserGrade(), user.getId());
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        MockHttpServletRequestBuilder builder = get("/payment/pay/bank");

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(bankAccount.getId()));
    }
}