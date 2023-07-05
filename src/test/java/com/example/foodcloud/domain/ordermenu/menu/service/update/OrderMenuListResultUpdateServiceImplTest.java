package com.example.foodcloud.domain.ordermenu.menu.service.update;

import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenuRepository;
import com.example.foodcloud.domain.groupbuylist.domain.GroupBuyList;
import com.example.foodcloud.domain.groupbuylist.domain.GroupBuyListRepository;
import com.example.foodcloud.domain.ordermenu.domain.OrderMenu;
import com.example.foodcloud.domain.ordermenu.domain.OrderMenuRepository;
import com.example.foodcloud.domain.ordermenu.service.update.OrderMenuListResultUpdateService;
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
class OrderMenuListResultUpdateServiceImplTest {
    private final OrderMenuListResultUpdateService orderMenuListResultUpdateService;
    private final OrderMenuRepository orderMenuRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final FoodMenuRepository foodMenuRepository;
    private final GroupBuyListRepository groupBuyListRepository;

    @Autowired
    public OrderMenuListResultUpdateServiceImplTest(OrderMenuListResultUpdateService orderMenuListResultUpdateService, OrderMenuRepository orderMenuRepository, UserRepository userRepository, RestaurantRepository restaurantRepository, FoodMenuRepository foodMenuRepository, GroupBuyListRepository groupBuyListRepository) {
        this.orderMenuListResultUpdateService = orderMenuListResultUpdateService;
        this.orderMenuRepository = orderMenuRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
        this.foodMenuRepository = foodMenuRepository;
        this.groupBuyListRepository = groupBuyListRepository;
    }

    @Test
    void 오더메뉴_업데이트_정상_작동() {
        User user = new User("test", "test", "test");
        userRepository.save(user);

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = new FoodMenu("test", 5000, Temperature.COLD, FoodTypes.ADE, MeatTypes.CHICKEN, Vegetables.FEW, restaurant);
        foodMenuRepository.save(foodMenu);

        GroupBuyList groupBuyList = new GroupBuyList("test", "test", user, restaurant);
        groupBuyListRepository.save(groupBuyList);

        OrderMenu orderMenu1 = new OrderMenu("test", 5, "test", user, foodMenu, groupBuyList);
        OrderMenu orderMenu2 = new OrderMenu("test", 5, "test", user, foodMenu, groupBuyList);
        orderMenuRepository.save(orderMenu1);
        orderMenuRepository.save(orderMenu2);

        orderMenuListResultUpdateService.update(groupBuyList.getId(), OrderResult.COOKING);

        assertEquals(OrderResult.COOKING, orderMenu1.getResult());
        assertEquals(OrderResult.COOKING, orderMenu2.getResult());
    }

    @Test
    void 오더메뉴_업데이트_오더메뉴_고유번호_다름() {
        User user = new User("test", "test", "test");
        userRepository.save(user);

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = new FoodMenu("test", 5000, Temperature.COLD, FoodTypes.ADE, MeatTypes.CHICKEN, Vegetables.FEW, restaurant);
        foodMenuRepository.save(foodMenu);

        GroupBuyList groupBuyList = new GroupBuyList("test", "test", user, restaurant);
        groupBuyListRepository.save(groupBuyList);

        OrderMenu orderMenu1 = new OrderMenu("test", 5, "test", user, foodMenu, groupBuyList);
        OrderMenu orderMenu2 = new OrderMenu("test", 5, "test", user, foodMenu, groupBuyList);
        orderMenuRepository.save(orderMenu1);
        orderMenuRepository.save(orderMenu2);

        orderMenuListResultUpdateService.update(groupBuyList.getId() + 1L, OrderResult.COOKING);

        assertNotEquals(OrderResult.COOKING, orderMenu1.getResult());
        assertNotEquals(OrderResult.COOKING, orderMenu2.getResult());
    }
}