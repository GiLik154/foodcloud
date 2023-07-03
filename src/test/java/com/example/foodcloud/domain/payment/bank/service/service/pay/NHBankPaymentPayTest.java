package com.example.foodcloud.domain.payment.bank.service.service.pay;

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
import com.example.foodcloud.enums.foodmenu.FoodTypes;
import com.example.foodcloud.enums.foodmenu.MeatTypes;
import com.example.foodcloud.enums.foodmenu.Temperature;
import com.example.foodcloud.enums.foodmenu.Vegetables;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class NHBankPaymentPayTest {
    private final Map<String, PaymentService> bankPaymentService;
    private final BankAccountRepository bankAccountRepository;
    private final RestaurantRepository restaurantRepository;
    private final FoodMenuRepository foodMenuRepository;
    private final OrderJoinGroupRepository orderJoinGroupRepository;
    private final OrderMenuRepository orderMenuRepository;
    private final UserRepository userRepository;

    @Autowired
    public NHBankPaymentPayTest(Map<String, PaymentService> bankPaymentService, BankAccountRepository bankAccountRepository, RestaurantRepository restaurantRepository, FoodMenuRepository foodMenuRepository, OrderJoinGroupRepository orderJoinGroupRepository, OrderMenuRepository orderMenuRepository, UserRepository userRepository) {
        this.bankPaymentService = bankPaymentService;
        this.bankAccountRepository = bankAccountRepository;
        this.restaurantRepository = restaurantRepository;
        this.foodMenuRepository = foodMenuRepository;
        this.orderJoinGroupRepository = orderJoinGroupRepository;
        this.orderMenuRepository = orderMenuRepository;
        this.userRepository = userRepository;
    }

    @Test
    void NH_결제_정상작동() {
        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();

        BankAccount bankAccount = new BankAccount(user, "testName", "testNumber", PaymentCode.NH);
        bankAccountRepository.save(bankAccount);
        Long bankAccountId = bankAccount.getId();

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = new FoodMenu("test", 5000, Temperature.COLD, FoodTypes.ADE, MeatTypes.CHICKEN, Vegetables.FEW, restaurant);
        foodMenuRepository.save(foodMenu);

        OrderJoinGroup orderJoinGroup = new OrderJoinGroup("test", "test", user, restaurant);
        orderJoinGroupRepository.save(orderJoinGroup);

        OrderMenu orderMenu = new OrderMenu("test", 5, "test", user, foodMenu, orderJoinGroup);
        orderMenuRepository.save(orderMenu);

        String result = bankPaymentService.get(PaymentCode.NH.getCode()).pay(userId, orderMenu.getId(), bankAccountId, 5000);

        assertEquals(bankAccount, orderMenu.getPayment());
        assertEquals(PaymentCode.NH, orderMenu.getPayment().getPaymentCode());
        assertEquals(OrderResult.RECEIVED, orderMenu.getResult());
        assertEquals("5000 price NH bank payment succeed", result);
    }

    @Test
    void NH_결제_계좌_고유번호_다름() {
        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();

        BankAccount bankAccount = new BankAccount(user, "testName", "testNumber", PaymentCode.NH);
        bankAccountRepository.save(bankAccount);
        Long bankAccountId = bankAccount.getId();

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = new FoodMenu("test", 5000, Temperature.COLD, FoodTypes.ADE, MeatTypes.CHICKEN, Vegetables.FEW, restaurant);
        foodMenuRepository.save(foodMenu);

        OrderJoinGroup orderJoinGroup = new OrderJoinGroup("test", "test", user, restaurant);
        orderJoinGroupRepository.save(orderJoinGroup);

        OrderMenu orderMenu = new OrderMenu("test", 5, "test", user, foodMenu, orderJoinGroup);
        orderMenuRepository.save(orderMenu);

        String result = bankPaymentService.get(PaymentCode.NH.getCode()).pay(userId, orderMenu.getId(), bankAccountId + 1L, 5000);

        assertNull(orderMenu.getPayment());
        assertNotEquals(OrderResult.RECEIVED, orderMenu.getResult());
        assertEquals("NH bank payment fail", result);
    }

    @Test
    void NH_결제_아이디_고유번호_다름() {
        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();

        BankAccount bankAccount = new BankAccount(user, "testName", "testNumber", PaymentCode.NH);
        bankAccountRepository.save(bankAccount);
        Long bankAccountId = bankAccount.getId();

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = new FoodMenu("test", 5000, Temperature.COLD, FoodTypes.ADE, MeatTypes.CHICKEN, Vegetables.FEW, restaurant);
        foodMenuRepository.save(foodMenu);

        OrderJoinGroup orderJoinGroup = new OrderJoinGroup("test", "test", user, restaurant);
        orderJoinGroupRepository.save(orderJoinGroup);

        OrderMenu orderMenu = new OrderMenu("test", 5, "test", user, foodMenu, orderJoinGroup);
        orderMenuRepository.save(orderMenu);

        String result = bankPaymentService.get(PaymentCode.NH.getCode()).pay(userId + 1L, orderMenu.getId(), bankAccountId, 5000);

        assertNull(orderMenu.getPayment());
        assertNotEquals(OrderResult.RECEIVED, orderMenu.getResult());
        assertEquals("NH bank payment fail", result);
    }
}