package com.example.foodcloud.domain.payment.bank.service.payment.pay;

import com.example.foodcloud.domain.payment.bank.domain.BankAccount;
import com.example.foodcloud.domain.payment.bank.domain.BankAccountRepository;
import com.example.foodcloud.domain.payment.bank.service.payment.PaymentService;
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
import com.example.foodcloud.enums.OrderResult;
import com.example.foodcloud.enums.foodmenu.FoodTypes;
import com.example.foodcloud.enums.foodmenu.MeatTypes;
import com.example.foodcloud.enums.foodmenu.Temperature;
import com.example.foodcloud.enums.foodmenu.Vegetables;
import com.example.foodcloud.exception.NotEnoughPointException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PointPayServiceTest {
    private final Map<String, PaymentService> bankPaymentService;
    private final BankAccountRepository bankAccountRepository;
    private final RestaurantRepository restaurantRepository;
    private final FoodMenuRepository foodMenuRepository;
    private final OrderMainRepository orderMainRepository;
    private final OrderMenuRepository orderMenuRepository;
    private final UserRepository userRepository;
    private final PointRepository pointRepository;

    @Autowired
    public PointPayServiceTest(Map<String, PaymentService> bankPaymentService, BankAccountRepository bankAccountRepository, RestaurantRepository restaurantRepository, FoodMenuRepository foodMenuRepository, OrderMainRepository orderMainRepository, OrderMenuRepository orderMenuRepository, UserRepository userRepository, PointRepository pointRepository) {
        this.bankPaymentService = bankPaymentService;
        this.bankAccountRepository = bankAccountRepository;
        this.restaurantRepository = restaurantRepository;
        this.foodMenuRepository = foodMenuRepository;
        this.orderMainRepository = orderMainRepository;
        this.orderMenuRepository = orderMenuRepository;
        this.userRepository = userRepository;
        this.pointRepository = pointRepository;
    }

    private final String BANK_CODE = "000";

    @Test
    void Point_결제_정상작동() {
        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();

        Point point = new Point(user);
        point.updatePoint(6000);
        pointRepository.save(point);

        BankAccount bankAccount = new BankAccount("test", "test", "004", user);
        bankAccountRepository.save(bankAccount);
        Long bankAccountId = bankAccount.getId();

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = new FoodMenu("test", 5000, Temperature.COLD, FoodTypes.ADE, MeatTypes.CHICKEN, Vegetables.FEW, restaurant);
        foodMenuRepository.save(foodMenu);

        OrderMain orderMain = new OrderMain("test", "test", user, restaurant);
        orderMainRepository.save(orderMain);

        OrderMenu orderMenu = new OrderMenu("test", 5, "test", user, foodMenu, orderMain);
        orderMenuRepository.save(orderMenu);

        String result = bankPaymentService.get(BANK_CODE).pay(userId, orderMenu.getId(), bankAccountId, 5000);

        assertEquals(point, orderMenu.getPoint());
        assertEquals(BANK_CODE, orderMenu.getPayment());
        assertEquals(OrderResult.RECEIVED.getResult(), orderMenu.getResult());
        assertEquals("5000 price Point payment succeed", result);
        assertEquals(1000, point.getTotalPoint());
        assertEquals(-5000, point.getCalculation());
    }

    @Test
    void Point_결제_아이디_고유번호_다름() {
        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();

        Point point = new Point(user);
        point.updatePoint(5000);
        pointRepository.save(point);

        BankAccount bankAccount = new BankAccount("test", "test", "004", user);
        bankAccountRepository.save(bankAccount);
        Long bankAccountId = bankAccount.getId();

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = new FoodMenu("test", 5000, Temperature.COLD, FoodTypes.ADE, MeatTypes.CHICKEN, Vegetables.FEW, restaurant);
        foodMenuRepository.save(foodMenu);

        OrderMain orderMain = new OrderMain("test", "test", user, restaurant);
        orderMainRepository.save(orderMain);

        OrderMenu orderMenu = new OrderMenu("test", 5, "test", user, foodMenu, orderMain);
        orderMenuRepository.save(orderMenu);

        String result = bankPaymentService.get(BANK_CODE).pay(userId + 1L, orderMenu.getId(), bankAccountId, 5000);

        assertNotEquals(point, orderMenu.getPoint());
        assertNull(orderMenu.getPayment());
        assertNotEquals(OrderResult.RECEIVED.getResult(), orderMenu.getResult());
        assertEquals("Point payment fail", result);
        assertEquals(5000, point.getTotalPoint());
        assertEquals(5000, point.getCalculation());
    }

    @Test
    void Point_포인트_부족() {
        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();

        Point point = new Point(user);
        point.updatePoint(1000);
        pointRepository.save(point);

        BankAccount bankAccount = new BankAccount("test", "test", "004", user);
        bankAccountRepository.save(bankAccount);
        Long bankAccountId = bankAccount.getId();

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = new FoodMenu("test", 5000, Temperature.COLD, FoodTypes.ADE, MeatTypes.CHICKEN, Vegetables.FEW, restaurant);
        foodMenuRepository.save(foodMenu);

        OrderMain orderMain = new OrderMain("test", "test", user, restaurant);
        orderMainRepository.save(orderMain);

        OrderMenu orderMenu = new OrderMenu("test", 5, "test", user, foodMenu, orderMain);
        orderMenuRepository.save(orderMenu);
        Long orderMenuId = orderMenu.getId();

        PaymentService paymentService = bankPaymentService.get(BANK_CODE);

        NotEnoughPointException exception = assertThrows(NotEnoughPointException.class, () ->
                paymentService.pay(userId, orderMenuId, bankAccountId, 5000)
        );

        assertNotEquals(point, orderMenu.getPoint());
        assertNull(orderMenu.getPayment());
        assertNotEquals(OrderResult.RECEIVED.getResult(), orderMenu.getResult());
        assertEquals("Out of bounds for point Points are less than zero.", exception.getMessage());
        assertEquals(1000, point.getTotalPoint());
        assertEquals(1000, point.getCalculation());
    }
}