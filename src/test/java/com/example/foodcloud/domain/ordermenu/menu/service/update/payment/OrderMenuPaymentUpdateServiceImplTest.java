package com.example.foodcloud.domain.ordermenu.menu.service.update.payment;

import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenuRepository;
import com.example.foodcloud.domain.groupbuylist.domain.GroupBuyList;
import com.example.foodcloud.domain.groupbuylist.domain.GroupBuyListRepository;
import com.example.foodcloud.domain.ordermenu.domain.OrderMenu;
import com.example.foodcloud.domain.ordermenu.domain.OrderMenuRepository;
import com.example.foodcloud.domain.ordermenu.service.update.payment.OrderMenuPaymentUpdateService;
import com.example.foodcloud.domain.payment.domain.BankAccount;
import com.example.foodcloud.domain.payment.domain.BankAccountRepository;
import com.example.foodcloud.domain.payment.domain.Point;
import com.example.foodcloud.domain.payment.domain.PointRepository;
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
    private final GroupBuyListRepository groupBuyListRepository;
    private final PointRepository pointRepository;
    private final BankAccountRepository bankAccountRepository;

    @Autowired
    public OrderMenuPaymentUpdateServiceImplTest(OrderMenuPaymentUpdateService orderMenuPaymentUpdateService, OrderMenuRepository orderMenuRepository, UserRepository userRepository, RestaurantRepository restaurantRepository, FoodMenuRepository foodMenuRepository, GroupBuyListRepository groupBuyListRepository, PointRepository pointRepository, BankAccountRepository bankAccountRepository) {
        this.orderMenuPaymentUpdateService = orderMenuPaymentUpdateService;
        this.orderMenuRepository = orderMenuRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
        this.foodMenuRepository = foodMenuRepository;
        this.groupBuyListRepository = groupBuyListRepository;
        this.pointRepository = pointRepository;
        this.bankAccountRepository = bankAccountRepository;
    }

    @Test
    void 결제수단_업데이트_포인트() {
        User user = new User("test", "test", "test");
        userRepository.save(user);

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = new FoodMenu("test", 5000, Temperature.COLD, FoodTypes.ADE, MeatTypes.CHICKEN, Vegetables.FEW, restaurant);
        foodMenuRepository.save(foodMenu);

        GroupBuyList groupBuyList = new GroupBuyList("test", "test", user, restaurant);
        groupBuyListRepository.save(groupBuyList);

        OrderMenu orderMenu = new OrderMenu("test", 5, "test", user, foodMenu, groupBuyList);
        orderMenuRepository.save(orderMenu);

        Point point = new Point(user);
        pointRepository.save(point);

        orderMenuPaymentUpdateService.update(orderMenu.getId(), point);

        assertEquals(PaymentCode.POINT, orderMenu.getPayment().getPaymentCode());
    }

    @Test
    void 결제수단_업데이트_계좌() {
        User user = new User("test", "test", "test");
        userRepository.save(user);

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = new FoodMenu("test", 5000, Temperature.COLD, FoodTypes.ADE, MeatTypes.CHICKEN, Vegetables.FEW, restaurant);
        foodMenuRepository.save(foodMenu);

        GroupBuyList groupBuyList = new GroupBuyList("test", "test", user, restaurant);
        groupBuyListRepository.save(groupBuyList);

        OrderMenu orderMenu = new OrderMenu("test", 5, "test", user, foodMenu, groupBuyList);
        orderMenuRepository.save(orderMenu);

        BankAccount bankAccount = new BankAccount(user, "testBankName", "testBankNumber", PaymentCode.KB);
        bankAccountRepository.save(bankAccount);

        orderMenuPaymentUpdateService.update(orderMenu.getId(), bankAccount);

        assertEquals(PaymentCode.KB, orderMenu.getPayment().getPaymentCode());
    }

    @Test
    void 결제수단_업데이트_주문번호_오류() {
        User user = new User("test", "test", "test");
        userRepository.save(user);

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = new FoodMenu("test", 5000, Temperature.COLD, FoodTypes.ADE, MeatTypes.CHICKEN, Vegetables.FEW, restaurant);
        foodMenuRepository.save(foodMenu);

        GroupBuyList groupBuyList = new GroupBuyList("test", "test", user, restaurant);
        groupBuyListRepository.save(groupBuyList);

        OrderMenu orderMenu = new OrderMenu("test", 5, "test", user, foodMenu, groupBuyList);
        orderMenuRepository.save(orderMenu);

        BankAccount bankAccount = new BankAccount(user, "testBankName", "testBankNumber", PaymentCode.KB);
        bankAccountRepository.save(bankAccount);

        orderMenuPaymentUpdateService.update(orderMenu.getId() + 1L, bankAccount);

        assertNull(orderMenu.getPayment());
    }
}