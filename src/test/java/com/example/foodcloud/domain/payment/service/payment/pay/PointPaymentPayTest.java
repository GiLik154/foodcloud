package com.example.foodcloud.domain.payment.service.payment.pay;

import com.example.foodcloud.*;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenuRepository;
import com.example.foodcloud.domain.groupbuylist.domain.GroupBuyList;
import com.example.foodcloud.domain.groupbuylist.domain.GroupBuyListRepository;
import com.example.foodcloud.domain.ordermenu.domain.OrderMenu;
import com.example.foodcloud.domain.ordermenu.domain.OrderMenuRepository;
import com.example.foodcloud.domain.payment.domain.BankAccountRepository;
import com.example.foodcloud.domain.payment.domain.Point;
import com.example.foodcloud.domain.payment.domain.PointRepository;
import com.example.foodcloud.domain.payment.service.payments.PaymentService;
import com.example.foodcloud.domain.restaurant.domain.Restaurant;
import com.example.foodcloud.domain.restaurant.domain.RestaurantRepository;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import com.example.foodcloud.enums.OrderResult;
import com.example.foodcloud.enums.PaymentCode;
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
class PointPaymentPayTest {
    private final Map<String, PaymentService> bankPaymentService;
    private final BankAccountRepository bankAccountRepository;
    private final RestaurantRepository restaurantRepository;
    private final FoodMenuRepository foodMenuRepository;
    private final GroupBuyListRepository groupBuyListRepository;
    private final OrderMenuRepository orderMenuRepository;
    private final UserRepository userRepository;
    private final PointRepository pointRepository;

    @Autowired
    public PointPaymentPayTest(Map<String, PaymentService> bankPaymentService, BankAccountRepository bankAccountRepository, RestaurantRepository restaurantRepository, FoodMenuRepository foodMenuRepository, GroupBuyListRepository groupBuyListRepository, OrderMenuRepository orderMenuRepository, UserRepository userRepository, PointRepository pointRepository) {
        this.bankPaymentService = bankPaymentService;
        this.bankAccountRepository = bankAccountRepository;
        this.restaurantRepository = restaurantRepository;
        this.foodMenuRepository = foodMenuRepository;
        this.groupBuyListRepository = groupBuyListRepository;
        this.orderMenuRepository = orderMenuRepository;
        this.userRepository = userRepository;
        this.pointRepository = pointRepository;
    }

    @Test
    void Point_결제_정상작동() {
        User user = userRepository.save(UserFixture.fixture().build());
        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());
        FoodMenu foodMenu = foodMenuRepository.save(FoodMenuFixture.fixture(restaurant).build());
        GroupBuyList groupBuyList = groupBuyListRepository.save(GroupBuyListFixture.fixture(user, restaurant).build());
        OrderMenu orderMenu = orderMenuRepository.save(OrderMenuFixture.fixture(user, groupBuyList, foodMenu).build());

        Point point = new Point(user);
        point.update(6000);
        pointRepository.save(point);

        Long orderMenuId = orderMenu.getId();
        Long pointId = point.getId();
        Long userId = user.getId();

        PaymentService service = bankPaymentService.get(PaymentCode.POINT.getCode());

        String result = service.pay(userId, orderMenuId, pointId, 5000);

        assertEquals(point, orderMenu.getPayment());
        assertEquals(PaymentCode.POINT, orderMenu.getPayment().getPaymentCode());
        assertEquals(OrderResult.RECEIVED, orderMenu.getResult());
        assertEquals("5000 price Point payment succeed", result);
        assertEquals(1000, point.getTotalPoint());
        assertEquals(-5000, point.getRecentPoint());
    }

    @Test
    void Point_결제_아이디_고유번호_다름() {
        User user = userRepository.save(UserFixture.fixture().build());
        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());
        FoodMenu foodMenu = foodMenuRepository.save(FoodMenuFixture.fixture(restaurant).build());
        GroupBuyList groupBuyList = groupBuyListRepository.save(GroupBuyListFixture.fixture(user, restaurant).build());
        OrderMenu orderMenu = orderMenuRepository.save(OrderMenuFixture.fixture(user, groupBuyList, foodMenu).build());

        Point point = new Point(user);
        point.update(5000);
        pointRepository.save(point);

        Long orderMenuId = orderMenu.getId();
        Long pointId = point.getId();
        Long userId = user.getId();

        PaymentService service = bankPaymentService.get(PaymentCode.POINT.getCode());

        String result = service.pay(userId + 1L, orderMenuId, pointId, 5000);

        assertNotEquals(point, orderMenu.getPayment());
        assertNotEquals(OrderResult.RECEIVED, orderMenu.getResult());
        assertEquals("Point payment fail", result);
        assertEquals(5000, point.getTotalPoint());
        assertEquals(5000, point.getRecentPoint());
    }

    @Test
    void Point_포인트_부족() {
        User user = userRepository.save(UserFixture.fixture().build());
        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());
        FoodMenu foodMenu = foodMenuRepository.save(FoodMenuFixture.fixture(restaurant).build());
        GroupBuyList groupBuyList = groupBuyListRepository.save(GroupBuyListFixture.fixture(user, restaurant).build());
        OrderMenu orderMenu = orderMenuRepository.save(OrderMenuFixture.fixture(user, groupBuyList, foodMenu).build());

        Point point = new Point(user);
        point.update(1000);
        pointRepository.save(point);

        Long orderMenuId = orderMenu.getId();
        Long pointId = point.getId();
        Long userId = user.getId();

        PaymentService service = bankPaymentService.get(PaymentCode.POINT.getCode());

        assertThrows(NotEnoughPointException.class, () -> service.pay(userId, orderMenuId, pointId, 5000));

        assertNotEquals(point, orderMenu.getPayment());
        assertNotEquals(OrderResult.RECEIVED, orderMenu.getResult());
        assertEquals(1000, point.getTotalPoint());
        assertEquals(1000, point.getRecentPoint());
    }
}