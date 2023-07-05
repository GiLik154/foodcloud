package com.example.foodcloud.domain.ordermenu.menu.service.delete;

import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenuRepository;
import com.example.foodcloud.domain.groupbuylist.domain.GroupBuyList;
import com.example.foodcloud.domain.groupbuylist.domain.GroupBuyListRepository;
import com.example.foodcloud.domain.ordermenu.domain.OrderMenu;
import com.example.foodcloud.domain.ordermenu.domain.OrderMenuRepository;
import com.example.foodcloud.domain.ordermenu.service.delete.OrderMenuDeleteService;
import com.example.foodcloud.domain.restaurant.domain.Restaurant;
import com.example.foodcloud.domain.restaurant.domain.RestaurantRepository;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
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
class OrderMenuDeleteServiceImplTest {
    private final OrderMenuDeleteService orderMenuDeleteService;
    private final OrderMenuRepository orderMenuRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final FoodMenuRepository foodMenuRepository;
    private final GroupBuyListRepository groupBuyListRepository;

    @Autowired
    public OrderMenuDeleteServiceImplTest(OrderMenuDeleteService orderMenuDeleteService, OrderMenuRepository orderMenuRepository, UserRepository userRepository, RestaurantRepository restaurantRepository, FoodMenuRepository foodMenuRepository, GroupBuyListRepository groupBuyListRepository) {
        this.orderMenuDeleteService = orderMenuDeleteService;
        this.orderMenuRepository = orderMenuRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
        this.foodMenuRepository = foodMenuRepository;
        this.groupBuyListRepository = groupBuyListRepository;
    }

    @Test
    void 오더메뉴_삭제_정상_작동() {
        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = new FoodMenu("test", 5000, Temperature.COLD, FoodTypes.ADE, MeatTypes.CHICKEN, Vegetables.FEW, restaurant);
        foodMenuRepository.save(foodMenu);

        GroupBuyList groupBuyList = new GroupBuyList("test", "test", user, restaurant);
        groupBuyListRepository.save(groupBuyList);

        OrderMenu orderMenu = new OrderMenu("test", 5, "test", user, foodMenu, groupBuyList);
        orderMenuRepository.save(orderMenu);
        Long orderMenuId = orderMenu.getId();

        orderMenuDeleteService.delete(userId, orderMenuId);

        assertFalse(orderMenuRepository.findById(orderMenuId).isPresent());
    }

    @Test
    void 오더메뉴_삭제_유저_고유번호_다름() {
        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = new FoodMenu("test", 5000, Temperature.COLD, FoodTypes.ADE, MeatTypes.CHICKEN, Vegetables.FEW, restaurant);
        foodMenuRepository.save(foodMenu);

        GroupBuyList groupBuyList = new GroupBuyList("test", "test", user, restaurant);
        groupBuyListRepository.save(groupBuyList);

        OrderMenu orderMenu = new OrderMenu("test", 5, "test", user, foodMenu, groupBuyList);
        orderMenuRepository.save(orderMenu);
        Long orderMenuId = orderMenu.getId();

        orderMenuDeleteService.delete(userId +1L, orderMenuId);

        assertTrue(orderMenuRepository.findById(orderMenuId).isPresent());
    }

    @Test
    void 오더메뉴_삭제_오더메뉴_고유번호_다름() {
        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = new FoodMenu("test", 5000, Temperature.COLD, FoodTypes.ADE, MeatTypes.CHICKEN, Vegetables.FEW, restaurant);
        foodMenuRepository.save(foodMenu);

        GroupBuyList groupBuyList = new GroupBuyList("test", "test", user, restaurant);
        groupBuyListRepository.save(groupBuyList);

        OrderMenu orderMenu = new OrderMenu("test", 5, "test", user, foodMenu, groupBuyList);
        orderMenuRepository.save(orderMenu);
        Long orderMenuId = orderMenu.getId();

        orderMenuDeleteService.delete(userId, orderMenuId + 1L);

        assertTrue(orderMenuRepository.findById(orderMenuId).isPresent());
    }
}