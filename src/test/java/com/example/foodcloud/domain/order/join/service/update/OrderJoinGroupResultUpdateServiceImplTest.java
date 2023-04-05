package com.example.foodcloud.domain.order.join.service.update;

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
class OrderJoinGroupResultUpdateServiceImplTest {
    private final OrderJoinGroupResultUpdateService orderJoinGroupResultUpdateService;
    private final OrderJoinGroupRepository orderJoinGroupRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final OrderMenuRepository orderMenuRepository;
    private final FoodMenuRepository foodMenuRepository;

    @Autowired
    public OrderJoinGroupResultUpdateServiceImplTest(OrderJoinGroupResultUpdateService OrderJoinGroupResultUpdateService,
                                                     OrderJoinGroupRepository OrderJoinGroupRepository,
                                                     UserRepository userRepository,
                                                     RestaurantRepository restaurantRepository,
                                                     OrderMenuRepository orderMenuRepository,
                                                     FoodMenuRepository foodMenuRepository) {
        this.orderJoinGroupResultUpdateService = OrderJoinGroupResultUpdateService;
        this.orderJoinGroupRepository = OrderJoinGroupRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
        this.orderMenuRepository = orderMenuRepository;
        this.foodMenuRepository = foodMenuRepository;
    }

    @Test
    void 메인주문_업데이트_정상_작동() {
        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);

        OrderJoinGroup orderJoinGroup = new OrderJoinGroup("test", "test", user, restaurant);
        orderJoinGroupRepository.save(orderJoinGroup);

        orderJoinGroupResultUpdateService.update(userId, orderJoinGroup.getId(), "PREPARED");

        assertEquals(OrderResult.PREPARED, orderJoinGroup.getResult());
    }

    @Test
    void 메인주문_업데이트_유저고유번호_다름() {
        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);

        OrderJoinGroup orderJoinGroup = new OrderJoinGroup("test", "test", user, restaurant);
        orderJoinGroupRepository.save(orderJoinGroup);

        orderJoinGroupResultUpdateService.update(userId + 1L, orderJoinGroup.getId(), "PREPARED");

        assertEquals(OrderResult.PAYMENT_WAITING, orderJoinGroup.getResult());
    }

    @Test
    void 메인주문_업데이트_오더고유번호_다름() {
        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);

        OrderJoinGroup orderJoinGroup = new OrderJoinGroup("test", "test", user, restaurant);
        orderJoinGroupRepository.save(orderJoinGroup);

        orderJoinGroupResultUpdateService.update(userId, orderJoinGroup.getId() + 1L, "PREPARED");

        assertEquals(OrderResult.PAYMENT_WAITING, orderJoinGroup.getResult());
    }

    @Test
    void 메인주문_업데이트_주문내역_변경() {
        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);

        OrderJoinGroup orderJoinGroup = new OrderJoinGroup("test", "test", user, restaurant);
        orderJoinGroupRepository.save(orderJoinGroup);

        FoodMenu foodMenu = new FoodMenu("test", 5000, Temperature.COLD, FoodTypes.ADE, MeatTypes.CHICKEN, Vegetables.FEW, restaurant);
        foodMenuRepository.save(foodMenu);

        OrderMenu orderMenu = new OrderMenu("test", 5, "test", user, foodMenu, orderJoinGroup);
        orderMenuRepository.save(orderMenu);

        orderJoinGroupResultUpdateService.update(userId, orderJoinGroup.getId(), "PREPARED");

        assertEquals(OrderResult.PREPARED, orderMenu.getResult());
    }
}