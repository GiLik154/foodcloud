package com.example.foodcloud.domain.order.menu.service.update.payment;

import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenuRepository;
import com.example.foodcloud.domain.order.main.domain.OrderMain;
import com.example.foodcloud.domain.order.main.domain.OrderMainRepository;
import com.example.foodcloud.domain.order.menu.domain.OrderMenu;
import com.example.foodcloud.domain.order.menu.domain.OrderMenuRepository;
import com.example.foodcloud.domain.payment.bank.domain.BankAccount;
import com.example.foodcloud.domain.payment.bank.domain.BankAccountRepository;
import com.example.foodcloud.domain.payment.point.domain.Point;
import com.example.foodcloud.domain.payment.point.domain.PointRepository;
import com.example.foodcloud.domain.restaurant.domain.Restaurant;
import com.example.foodcloud.domain.restaurant.domain.RestaurantRepository;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderMenuPaymentUpdateServiceImplTest {
    private final OrderMenuPaymentUpdateService orderMenuPaymentUpdateService;
    private final OrderMenuRepository orderMenuRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final FoodMenuRepository foodMenuRepository;
    private final OrderMainRepository orderMainRepository;
    private final PointRepository pointRepository;
    private final BankAccountRepository bankAccountRepository;

    @Autowired
    public OrderMenuPaymentUpdateServiceImplTest(OrderMenuPaymentUpdateService orderMenuPaymentUpdateService, OrderMenuRepository orderMenuRepository, UserRepository userRepository, RestaurantRepository restaurantRepository, FoodMenuRepository foodMenuRepository, OrderMainRepository orderMainRepository, PointRepository pointRepository, BankAccountRepository bankAccountRepository) {
        this.orderMenuPaymentUpdateService = orderMenuPaymentUpdateService;
        this.orderMenuRepository = orderMenuRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
        this.foodMenuRepository = foodMenuRepository;
        this.orderMainRepository = orderMainRepository;
        this.pointRepository = pointRepository;
        this.bankAccountRepository = bankAccountRepository;
    }

    @Test
    void 결제수단_업데이트_포인트() {
        User user = new User("test", "test", "test");
        userRepository.save(user);

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = new FoodMenu("test", 5000, "test", "test", "test", "test", restaurant);
        foodMenuRepository.save(foodMenu);

        OrderMain orderMain = new OrderMain("test", "test", user, restaurant);
        orderMainRepository.save(orderMain);

        OrderMenu orderMenu = new OrderMenu("test", 5, "test", user, foodMenu, orderMain);
        orderMenuRepository.save(orderMenu);

        Point point = new Point(user);
        pointRepository.save(point);

        boolean isUpdate = orderMenuPaymentUpdateService.isUpdate(orderMenu.getId(), point);

        assertTrue(isUpdate);
        assertEquals("000", orderMenu.getPayment());
    }

    @Test
    void 결제수단_업데이트_계좌() {
        User user = new User("test", "test", "test");
        userRepository.save(user);

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = new FoodMenu("test", 5000, "test", "test", "test", "test", restaurant);
        foodMenuRepository.save(foodMenu);

        OrderMain orderMain = new OrderMain("test", "test", user, restaurant);
        orderMainRepository.save(orderMain);

        OrderMenu orderMenu = new OrderMenu("test", 5, "test", user, foodMenu, orderMain);
        orderMenuRepository.save(orderMenu);

        BankAccount bankAccount = new BankAccount("test", "test", "004", user);
        bankAccountRepository.save(bankAccount);

        boolean isUpdate = orderMenuPaymentUpdateService.isUpdate(orderMenu.getId(), bankAccount);

        assertTrue(isUpdate);
        assertEquals("004", orderMenu.getPayment());
    }

    @Test
    void 결제수단_업데이트_주문번호_오류() {
        User user = new User("test", "test", "test");
        userRepository.save(user);

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = new FoodMenu("test", 5000, "test", "test", "test", "test", restaurant);
        foodMenuRepository.save(foodMenu);

        OrderMain orderMain = new OrderMain("test", "test", user, restaurant);
        orderMainRepository.save(orderMain);

        OrderMenu orderMenu = new OrderMenu("test", 5, "test", user, foodMenu, orderMain);
        orderMenuRepository.save(orderMenu);

        BankAccount bankAccount = new BankAccount("test", "test", "004", user);
        bankAccountRepository.save(bankAccount);

        boolean isUpdate = orderMenuPaymentUpdateService.isUpdate(orderMenu.getId() + 1L, bankAccount);

        assertFalse(isUpdate);
        assertNotEquals("004", orderMenu.getPayment());
    }
}