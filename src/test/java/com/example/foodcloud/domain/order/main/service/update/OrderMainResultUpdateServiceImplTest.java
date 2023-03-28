package com.example.foodcloud.domain.order.main.service.update;

import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenuRepository;
import com.example.foodcloud.domain.order.main.domain.OrderMain;
import com.example.foodcloud.domain.order.main.domain.OrderMainRepository;
import com.example.foodcloud.domain.order.menu.menu.domain.OrderMenu;
import com.example.foodcloud.domain.order.menu.menu.domain.OrderMenuRepository;
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
class OrderMainResultUpdateServiceImplTest {
    private final OrderMainResultUpdateService orderMainResultUpdateService;
    private final OrderMainRepository orderMainRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final OrderMenuRepository orderMenuRepository;
    private final FoodMenuRepository foodMenuRepository;

    @Autowired
    public OrderMainResultUpdateServiceImplTest(OrderMainResultUpdateService OrderMainResultUpdateService,
                                                OrderMainRepository OrderMainRepository,
                                                UserRepository userRepository,
                                                RestaurantRepository restaurantRepository,
                                                OrderMenuRepository orderMenuRepository,
                                                FoodMenuRepository foodMenuRepository) {
        this.orderMainResultUpdateService = OrderMainResultUpdateService;
        this.orderMainRepository = OrderMainRepository;
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

        OrderMain orderMain = new OrderMain("test", "test", user, restaurant);
        orderMainRepository.save(orderMain);

        boolean isUpdate = orderMainResultUpdateService.update(userId, orderMain.getId(), "PREPARED");

        assertTrue(isUpdate);
        assertEquals("Prepared", orderMain.getResult());
    }

    @Test
    void 메인주문_업데이트_유저고유번호_다름() {
        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);

        OrderMain orderMain = new OrderMain("test", "test", user, restaurant);
        orderMainRepository.save(orderMain);

        boolean isUpdate = orderMainResultUpdateService.update(userId + 1L, orderMain.getId(), "PREPARED");

        assertFalse(isUpdate);
        assertEquals("Payment waiting", orderMain.getResult());
    }

    @Test
    void 메인주문_업데이트_오더고유번호_다름() {
        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);

        OrderMain orderMain = new OrderMain("test", "test", user, restaurant);
        orderMainRepository.save(orderMain);

        boolean isUpdate = orderMainResultUpdateService.update(userId, orderMain.getId() + 1L, "PREPARED");

        assertFalse(isUpdate);
        assertEquals("Payment waiting", orderMain.getResult());
    }

    @Test
    void 메인주문_업데이트_주문내역_변경() {
        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);

        OrderMain orderMain = new OrderMain("test", "test", user, restaurant);
        orderMainRepository.save(orderMain);

        FoodMenu foodMenu = new FoodMenu("test", 5000, "test", "test", "test", "test", restaurant);
        foodMenuRepository.save(foodMenu);

        OrderMenu orderMenu = new OrderMenu("test", 5, "test", user, foodMenu, orderMain);
        orderMenuRepository.save(orderMenu);

        boolean isUpdate = orderMainResultUpdateService.update(userId, orderMain.getId(), "PREPARED");

        assertTrue(isUpdate);
        assertEquals("Prepared", orderMenu.getResult());
    }
}