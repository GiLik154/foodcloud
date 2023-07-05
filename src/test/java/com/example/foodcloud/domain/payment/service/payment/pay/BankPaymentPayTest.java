package com.example.foodcloud.domain.payment.service.payment.pay;

import com.example.foodcloud.*;
import com.example.foodcloud.domain.payment.domain.BankAccount;
import com.example.foodcloud.domain.payment.domain.BankAccountRepository;
import com.example.foodcloud.domain.payment.service.payments.PaymentService;
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
import com.example.foodcloud.enums.OrderResult;
import com.example.foodcloud.enums.PaymentCode;
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
    private final GroupBuyListRepository groupBuyListRepository;
    private final OrderMenuRepository orderMenuRepository;
    private final UserRepository userRepository;

    @Autowired
    public BankPaymentPayTest(Map<String, PaymentService> bankPaymentService, BankAccountRepository bankAccountRepository, RestaurantRepository restaurantRepository, FoodMenuRepository foodMenuRepository, GroupBuyListRepository groupBuyListRepository, OrderMenuRepository orderMenuRepository, UserRepository userRepository) {
        this.bankPaymentService = bankPaymentService;
        this.bankAccountRepository = bankAccountRepository;
        this.restaurantRepository = restaurantRepository;
        this.foodMenuRepository = foodMenuRepository;
        this.groupBuyListRepository = groupBuyListRepository;
        this.orderMenuRepository = orderMenuRepository;
        this.userRepository = userRepository;
    }

    private final int INIT_PRICE = 5000;

    @ParameterizedTest
    @ValueSource(strings = {"KB", "NH", "SHIN_HAN"})
    void 계좌_결제_정상작동(String paymentCode) {
        User user = userRepository.save(UserFixture.fixture().build());
        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());
        GroupBuyList groupBuyList = groupBuyListRepository.save(GroupBuyListFixture.fixture(user, restaurant).build());
        FoodMenu foodMenu = foodMenuRepository.save(FoodMenuFixture.fixture(restaurant).build());
        OrderMenu orderMenu = orderMenuRepository.save(OrderMenuFixture.fixture(user, groupBuyList, foodMenu).build());

        PaymentCode code = PaymentCode.valueOf(paymentCode);
        PaymentService service = bankPaymentService.get(code.getCode());

        BankAccount bankAccount = bankAccountRepository.save(BankAccountFixture.fixture(user, code).build());

        Long userId = user.getId();
        Long bankAccountId = bankAccount.getId();
        Long orderMenuId = orderMenu.getId();

        String result = service.pay(userId, orderMenuId, bankAccountId, INIT_PRICE);

        assertEquals(bankAccount, orderMenu.getPayment());
        assertEquals(code, orderMenu.getPayment().getPaymentCode());
        assertEquals(OrderResult.RECEIVED, orderMenu.getResult());
        assertTrue(result.contains("succeed"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"KB", "NH", "SHIN_HAN"})
    void 계좌의_고유번호가_다르면_걸제에_실패함(String paymentCode) {
        User user = userRepository.save(UserFixture.fixture().build());
        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());
        GroupBuyList groupBuyList = groupBuyListRepository.save(GroupBuyListFixture.fixture(user, restaurant).build());
        FoodMenu foodMenu = foodMenuRepository.save(FoodMenuFixture.fixture(restaurant).build());

        PaymentCode code = PaymentCode.valueOf(paymentCode);
        PaymentService service = bankPaymentService.get(code.getCode());

        BankAccount bankAccount = bankAccountRepository.save(BankAccountFixture.fixture(user, code).build());

        OrderMenu orderMenu = orderMenuRepository.save(OrderMenuFixture.fixture(user, groupBuyList, foodMenu).build());

        Long userId = user.getId();
        Long bankAccountId = bankAccount.getId();
        Long orderMenuId = orderMenu.getId();

        String result = service.pay(userId, orderMenuId, bankAccountId + 1L, INIT_PRICE);

        assertNull(orderMenu.getPayment());
        assertNotEquals(OrderResult.RECEIVED, orderMenu.getResult());
        assertTrue(result.contains("fail"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"KB", "NH", "SHIN_HAN"})
    void 유저의_고유번호가_다르면_걸제에_실패함(String paymentCode) {
        User user = userRepository.save(UserFixture.fixture().build());
        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());
        GroupBuyList groupBuyList = groupBuyListRepository.save(GroupBuyListFixture.fixture(user, restaurant).build());
        FoodMenu foodMenu = foodMenuRepository.save(FoodMenuFixture.fixture(restaurant).build());
        OrderMenu orderMenu = orderMenuRepository.save(OrderMenuFixture.fixture(user, groupBuyList, foodMenu).build());

        PaymentCode code = PaymentCode.valueOf(paymentCode);
        PaymentService service = bankPaymentService.get(code.getCode());

        BankAccount bankAccount = bankAccountRepository.save(BankAccountFixture.fixture(user, code).build());

        Long userId = user.getId();
        Long bankAccountId = bankAccount.getId();
        Long orderMenuId = orderMenu.getId();

        String result = service.pay(userId + 1L, orderMenuId, bankAccountId, INIT_PRICE);

        assertNotEquals(bankAccount, orderMenu.getPayment());
        assertNull(orderMenu.getPayment());
        assertNotEquals(OrderResult.RECEIVED, orderMenu.getResult());
        assertTrue(result.contains("fail"));
    }
}