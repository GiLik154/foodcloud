package com.example.foodcloud.domain.ordermenu.service;

import com.example.foodcloud.*;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenuRepository;
import com.example.foodcloud.domain.groupbuylist.domain.GroupBuyList;
import com.example.foodcloud.domain.groupbuylist.domain.GroupBuyListRepository;
import com.example.foodcloud.domain.ordermenu.domain.OrderMenu;
import com.example.foodcloud.domain.ordermenu.domain.OrderMenuRepository;
import com.example.foodcloud.domain.ordermenu.service.OrderMenuDeleter;
import com.example.foodcloud.domain.restaurant.domain.Restaurant;
import com.example.foodcloud.domain.restaurant.domain.RestaurantRepository;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderMenuDeleterTest {
    private final OrderMenuDeleter orderMenuDeleter;
    private final OrderMenuRepository orderMenuRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final FoodMenuRepository foodMenuRepository;
    private final GroupBuyListRepository groupBuyListRepository;

    @Autowired
    public OrderMenuDeleterTest(OrderMenuDeleter orderMenuDeleter, OrderMenuRepository orderMenuRepository, UserRepository userRepository, RestaurantRepository restaurantRepository, FoodMenuRepository foodMenuRepository, GroupBuyListRepository groupBuyListRepository) {
        this.orderMenuDeleter = orderMenuDeleter;
        this.orderMenuRepository = orderMenuRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
        this.foodMenuRepository = foodMenuRepository;
        this.groupBuyListRepository = groupBuyListRepository;
    }

    @Test
    void 오더메뉴_삭제_정상_작동() {
        User user = userRepository.save(UserFixture.fixture().build());
        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());
        FoodMenu foodMenu = foodMenuRepository.save(FoodMenuFixture.fixture(restaurant).build());
        GroupBuyList groupBuyList = groupBuyListRepository.save(GroupBuyListFixture.fixture(user, restaurant).build());
        OrderMenu orderMenu = orderMenuRepository.save(OrderMenuFixture.fixture(user, groupBuyList, foodMenu).build());

        Long userId = user.getId();
        Long orderMenuId = orderMenu.getId();

        orderMenuDeleter.delete(userId, orderMenuId);

        assertFalse(orderMenuRepository.findById(orderMenuId).isPresent());
    }

    @Test
    void 오더메뉴_삭제_유저_고유번호_다름() {
        User user = userRepository.save(UserFixture.fixture().build());
        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());
        FoodMenu foodMenu = foodMenuRepository.save(FoodMenuFixture.fixture(restaurant).build());
        GroupBuyList groupBuyList = groupBuyListRepository.save(GroupBuyListFixture.fixture(user, restaurant).build());
        OrderMenu orderMenu = orderMenuRepository.save(OrderMenuFixture.fixture(user, groupBuyList, foodMenu).build());

        Long userId = user.getId();
        Long orderMenuId = orderMenu.getId();

        orderMenuDeleter.delete(userId +1L, orderMenuId);

        assertTrue(orderMenuRepository.findById(orderMenuId).isPresent());
    }

    @Test
    void 오더메뉴_삭제_오더메뉴_고유번호_다름() {
        User user = userRepository.save(UserFixture.fixture().build());
        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());
        FoodMenu foodMenu = foodMenuRepository.save(FoodMenuFixture.fixture(restaurant).build());
        GroupBuyList groupBuyList = groupBuyListRepository.save(GroupBuyListFixture.fixture(user, restaurant).build());
        OrderMenu orderMenu = orderMenuRepository.save(OrderMenuFixture.fixture(user, groupBuyList, foodMenu).build());

        Long userId = user.getId();
        Long orderMenuId = orderMenu.getId();

        orderMenuDeleter.delete(userId, orderMenuId + 1L);

        assertTrue(orderMenuRepository.findById(orderMenuId).isPresent());
    }
}