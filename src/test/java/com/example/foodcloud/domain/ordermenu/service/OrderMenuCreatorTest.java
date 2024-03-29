package com.example.foodcloud.domain.ordermenu.service;

import com.example.foodcloud.FoodMenuFixture;
import com.example.foodcloud.GroupBuyListFixture;
import com.example.foodcloud.RestaurantFixture;
import com.example.foodcloud.UserFixture;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenuRepository;
import com.example.foodcloud.domain.groupbuylist.domain.GroupBuyList;
import com.example.foodcloud.domain.groupbuylist.domain.GroupBuyListRepository;
import com.example.foodcloud.domain.ordermenu.domain.OrderMenu;
import com.example.foodcloud.domain.ordermenu.domain.OrderMenuRepository;
import com.example.foodcloud.domain.ordermenu.service.commend.OrderMenuCreatorCommend;
import com.example.foodcloud.domain.restaurant.domain.Restaurant;
import com.example.foodcloud.domain.restaurant.domain.RestaurantRepository;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import com.example.foodcloud.enums.OrderResult;
import com.example.foodcloud.exception.NotFoundFoodMenuException;
import com.example.foodcloud.exception.NotFoundGroupByListException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderMenuCreatorTest {
    private final OrderMenuCreator orderMenuCreator;
    private final OrderMenuRepository orderMenuRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final FoodMenuRepository foodMenuRepository;
    private final GroupBuyListRepository groupBuyListRepository;

    @Autowired
    OrderMenuCreatorTest(OrderMenuCreator orderMenuCreator, OrderMenuRepository orderMenuRepository, UserRepository userRepository, RestaurantRepository restaurantRepository, FoodMenuRepository foodMenuRepository, GroupBuyListRepository groupBuyListRepository) {
        this.orderMenuCreator = orderMenuCreator;
        this.orderMenuRepository = orderMenuRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
        this.foodMenuRepository = foodMenuRepository;
        this.groupBuyListRepository = groupBuyListRepository;
    }

    @Test
    void 주문메뉴_추가_정상작동() {
        User user = userRepository.save(UserFixture.fixture().build());
        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());
        FoodMenu foodMenu = foodMenuRepository.save(FoodMenuFixture.fixture(restaurant).build());
        GroupBuyList groupBuyList = groupBuyListRepository.save(GroupBuyListFixture.fixture(user, restaurant).build());

        Long foodMenuId = foodMenu.getId();
        Long groupBuyListId = groupBuyList.getId();

        OrderMenuCreatorCommend orderMenuCreatorCommend = new OrderMenuCreatorCommend("testLocation", 5, foodMenuId, groupBuyListId);
        orderMenuCreator.crate(user.getId(), orderMenuCreatorCommend);

        OrderMenu orderMenu = orderMenuRepository.findAll().get(0);

        assertEquals("testLocation", orderMenu.getLocation());
        assertEquals(5, orderMenu.getCount());
        assertEquals(user, orderMenu.getUser());
        assertEquals(foodMenu, orderMenu.getFoodMenu());
        assertEquals(groupBuyList, orderMenu.getGroupBuyList());
        assertEquals(25000, orderMenu.getPrice());
        assertEquals(OrderResult.PAYMENT_WAITING, orderMenu.getResult());
        assertNotNull(orderMenu.getTime());

        assertEquals(1, foodMenu.getOrderCount());
        assertEquals(1, foodMenu.getRestaurant().getOrderCount());
    }

    @Test
    void 주문메뉴_추가_유저고유번호_다름() {
        User user = userRepository.save(UserFixture.fixture().build());
        userRepository.save(user);
        Long userId = user.getId();

        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = foodMenuRepository.save(FoodMenuFixture.fixture(restaurant).build());
        foodMenuRepository.save(foodMenu);

        GroupBuyList groupBuyList = groupBuyListRepository.save(GroupBuyListFixture.fixture(user, restaurant).build());
        groupBuyListRepository.save(groupBuyList);

        OrderMenuCreatorCommend orderMenuCreatorCommend = new OrderMenuCreatorCommend("test", 5, foodMenu.getId(), groupBuyList.getId());
        orderMenuCreator.crate(user.getId(), orderMenuCreatorCommend);

        assertThrows(UsernameNotFoundException.class, () ->
                orderMenuCreator.crate(userId + 1L, orderMenuCreatorCommend)
        );

    }

    @Test
    void 주문메뉴_추가_음식메뉴_고유번호_다름() {
        User user = userRepository.save(UserFixture.fixture().build());
        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());
        FoodMenu foodMenu = foodMenuRepository.save(FoodMenuFixture.fixture(restaurant).build());
        GroupBuyList groupBuyList = groupBuyListRepository.save(GroupBuyListFixture.fixture(user, restaurant).build());

        Long userId = user.getId();
        Long foodMenuId = foodMenu.getId();
        Long groupBuyListId = groupBuyList.getId();

        OrderMenuCreatorCommend orderMenuCreatorCommend = new OrderMenuCreatorCommend("testLocation", 5, foodMenuId + 1L, groupBuyListId);

        assertThrows(NotFoundFoodMenuException.class, () -> orderMenuCreator.crate(userId, orderMenuCreatorCommend));
    }

    @Test
    void 주문메뉴_추가_오더메인_고유번호_다름() {
        User user = userRepository.save(UserFixture.fixture().build());
        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());
        FoodMenu foodMenu = foodMenuRepository.save(FoodMenuFixture.fixture(restaurant).build());
        GroupBuyList groupBuyList = groupBuyListRepository.save(GroupBuyListFixture.fixture(user, restaurant).build());

        Long userId = user.getId();
        Long foodMenuId = foodMenu.getId();
        Long groupBuyListId = groupBuyList.getId();

        OrderMenuCreatorCommend orderMenuCreatorCommend = new OrderMenuCreatorCommend("testLocation", 5, foodMenuId, groupBuyListId + 1L);

        assertThrows(NotFoundGroupByListException.class, () -> orderMenuCreator.crate(userId, orderMenuCreatorCommend));
    }
}