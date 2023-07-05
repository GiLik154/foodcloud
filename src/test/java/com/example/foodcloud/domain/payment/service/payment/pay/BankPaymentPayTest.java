package com.example.foodcloud.domain.payment.service.payment.pay;

import com.example.foodcloud.BankAccountFixtures;
import com.example.foodcloud.FoodMenuFixtures;
import com.example.foodcloud.RestaurantFixtures;
import com.example.foodcloud.UserFixtures;
import com.example.foodcloud.domain.payment.domain.BankAccount;
import com.example.foodcloud.domain.payment.domain.BankAccountRepository;
import com.example.foodcloud.domain.payment.service.payments.PaymentService;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenuRepository;
import com.example.foodcloud.domain.order.join.domain.OrderJoinGroup;
import com.example.foodcloud.domain.order.join.domain.OrderJoinGroupRepository;
import com.example.foodcloud.domain.order.menu.domain.OrderMenu;
import com.example.foodcloud.domain.order.menu.domain.OrderMenuRepository;
import com.example.foodcloud.domain.restaurant.domain.Restaurant;
import com.example.foodcloud.domain.restaurant.domain.RestaurantRepository;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import com.example.foodcloud.enums.OrderResult;
import com.example.foodcloud.enums.PaymentCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BankPaymentPayTest {
    private final Map<String, PaymentService> bankPaymentService;
    private final BankAccountRepository bankAccountRepository;
    private final RestaurantRepository restaurantRepository;
    private final FoodMenuRepository foodMenuRepository;
    private final OrderJoinGroupRepository orderJoinGroupRepository;
    private final OrderMenuRepository orderMenuRepository;
    private final UserRepository userRepository;

    @Autowired
    public BankPaymentPayTest(Map<String, PaymentService> bankPaymentService, BankAccountRepository bankAccountRepository, RestaurantRepository restaurantRepository, FoodMenuRepository foodMenuRepository, OrderJoinGroupRepository orderJoinGroupRepository, OrderMenuRepository orderMenuRepository, UserRepository userRepository) {
        this.bankPaymentService = bankPaymentService;
        this.bankAccountRepository = bankAccountRepository;
        this.restaurantRepository = restaurantRepository;
        this.foodMenuRepository = foodMenuRepository;
        this.orderJoinGroupRepository = orderJoinGroupRepository;
        this.orderMenuRepository = orderMenuRepository;
        this.userRepository = userRepository;
    }

    private final int INIT_PRICE = 5000;
    private User user;
    private OrderMenu orderMenu;

    @BeforeEach
    public void init() {
        user = UserFixtures.fixtures().build();
        userRepository.save(user);

        Restaurant restaurant = RestaurantFixtures.fixtures(user).build();
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = FoodMenuFixtures.fixtures(restaurant).build();
        foodMenuRepository.save(foodMenu);

        OrderJoinGroup orderJoinGroup = new OrderJoinGroup("test", "test", user, restaurant);
        orderJoinGroupRepository.save(orderJoinGroup);

        orderMenu = new OrderMenu("test", 5, "test", user, foodMenu, orderJoinGroup);
        orderMenuRepository.save(orderMenu);
    }

    @ParameterizedTest
    @ValueSource(strings = {"KB", "NH", "SHIN_HAN"})
    void 계좌_결제_정상작동(String paymentCode) {
        PaymentCode code = PaymentCode.valueOf(paymentCode);

        Long userId = user.getId();
        Long orderMenuId = orderMenu.getId();

        BankAccount bankAccount = BankAccountFixtures.fixtures(user, code).build();
        bankAccountRepository.save(bankAccount);
        Long bankAccountId = bankAccount.getId();

        PaymentService service = bankPaymentService.get(code.getCode());

        String result = service.pay(userId, orderMenuId, bankAccountId, INIT_PRICE);

        assertEquals(bankAccount, orderMenu.getPayment());
        assertEquals(code, orderMenu.getPayment().getPaymentCode());
        assertEquals(OrderResult.RECEIVED, orderMenu.getResult());
        assertTrue(result.contains("succeed"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"KB", "NH", "SHIN_HAN"})
    void 계좌의_고유번호가_다르면_걸제에_실패함(String paymentCode) {
        Long userId = user.getId();
        Long orderMenuId = orderMenu.getId();

        PaymentCode code = PaymentCode.valueOf(paymentCode);

        BankAccount bankAccount = BankAccountFixtures.fixtures(user, code).build();
        bankAccountRepository.save(bankAccount);
        Long bankAccountId = bankAccount.getId();

        String result = bankPaymentService.get(PaymentCode.KB.getCode()).pay(userId, orderMenuId, bankAccountId + 1L, INIT_PRICE);

        assertNull(orderMenu.getPayment());
        assertNotEquals(OrderResult.RECEIVED, orderMenu.getResult());
        assertTrue(result.contains("fail"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"KB", "NH", "SHIN_HAN"})
    void 유저의_고유번호가_다르면_걸제에_실패함(String paymentCode) {
        Long userId = user.getId();
        Long orderMenuId = orderMenu.getId();

        PaymentCode code = PaymentCode.valueOf(paymentCode);

        BankAccount bankAccount = BankAccountFixtures.fixtures(user, code).build();
        bankAccountRepository.save(bankAccount);
        Long bankAccountId = bankAccount.getId();

        String result = bankPaymentService.get(PaymentCode.KB.getCode()).pay(userId + 1L, orderMenuId, bankAccountId, INIT_PRICE);

        assertNotEquals(bankAccount, orderMenu.getPayment());
        assertNull(orderMenu.getPayment());
        assertNotEquals(OrderResult.RECEIVED, orderMenu.getResult());
        assertTrue(result.contains("fail"));
    }
}