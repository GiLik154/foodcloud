package com.example.foodcloud.domain.payment.bank.service.service.refund;

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

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ShinHanBankPaymentRefundTest {
    private final Map<String, PaymentService> bankPaymentService;
    private final BankAccountRepository bankAccountRepository;
    private final RestaurantRepository restaurantRepository;
    private final FoodMenuRepository foodMenuRepository;
    private final OrderJoinGroupRepository orderJoinGroupRepository;
    private final OrderMenuRepository orderMenuRepository;
    private final UserRepository userRepository;

    @Autowired
    public ShinHanBankPaymentRefundTest(Map<String, PaymentService> bankPaymentService, BankAccountRepository bankAccountRepository, RestaurantRepository restaurantRepository, FoodMenuRepository foodMenuRepository, OrderJoinGroupRepository orderJoinGroupRepository, OrderMenuRepository orderMenuRepository, UserRepository userRepository) {
        this.bankPaymentService = bankPaymentService;
        this.bankAccountRepository = bankAccountRepository;
        this.restaurantRepository = restaurantRepository;
        this.foodMenuRepository = foodMenuRepository;
        this.orderJoinGroupRepository = orderJoinGroupRepository;
        this.orderMenuRepository = orderMenuRepository;
        this.userRepository = userRepository;
    }

    @Test
    void ShinHan_결제_정상작동() {
        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();

        BankAccount bankAccount = new BankAccount(user, "testName", "testNumber", PaymentCode.SHIN_HAN);
        bankAccountRepository.save(bankAccount);

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = new FoodMenu("test", 5000, Temperature.COLD, FoodTypes.ADE, MeatTypes.CHICKEN, Vegetables.FEW, restaurant);
        foodMenuRepository.save(foodMenu);

        OrderJoinGroup orderJoinGroup = new OrderJoinGroup("test", "test", user, restaurant);
        orderJoinGroupRepository.save(orderJoinGroup);

        OrderMenu orderMenu = new OrderMenu("test", 5, "test", user, foodMenu, orderJoinGroup);
        orderMenu.completeOrderWithPayment(bankAccount);
        orderMenuRepository.save(orderMenu);

        String result = bankPaymentService.get(PaymentCode.SHIN_HAN.getCode()).refund(userId, orderMenu);

        assertEquals("25000 price ShinHan bank refund succeed", result);
    }

    @Test
    void ShinHan_결제_아이디_고유번호_다름() {
        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();

        BankAccount bankAccount = new BankAccount(user, "testName", "testNumber", PaymentCode.SHIN_HAN);
        bankAccountRepository.save(bankAccount);

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = new FoodMenu("test", 5000, Temperature.COLD, FoodTypes.ADE, MeatTypes.CHICKEN, Vegetables.FEW, restaurant);
        foodMenuRepository.save(foodMenu);

        OrderJoinGroup orderJoinGroup = new OrderJoinGroup("test", "test", user, restaurant);
        orderJoinGroupRepository.save(orderJoinGroup);

        OrderMenu orderMenu = new OrderMenu("test", 5, "test", user, foodMenu, orderJoinGroup);
        orderMenu.completeOrderWithPayment(bankAccount);
        orderMenuRepository.save(orderMenu);

        String result = bankPaymentService.get(PaymentCode.SHIN_HAN.getCode()).refund(userId + 1L, orderMenu);

        assertEquals("ShinHan bank refund fail", result);
    }
}