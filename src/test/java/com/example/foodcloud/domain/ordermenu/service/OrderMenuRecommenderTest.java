package com.example.foodcloud.domain.ordermenu.service;

import com.example.foodcloud.*;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenuRepository;
import com.example.foodcloud.domain.groupbuylist.domain.GroupBuyList;
import com.example.foodcloud.domain.groupbuylist.domain.GroupBuyListRepository;
import com.example.foodcloud.domain.ordermenu.domain.OrderMenu;
import com.example.foodcloud.domain.ordermenu.domain.OrderMenuRepository;
import com.example.foodcloud.domain.restaurant.domain.Restaurant;
import com.example.foodcloud.domain.restaurant.domain.RestaurantRepository;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderMenuRecommenderTest {
    private final OrderMenuRecommender orderMenuRecommender;
    private final OrderMenuRepository orderMenuRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final FoodMenuRepository foodMenuRepository;
    private final GroupBuyListRepository groupBuyListRepository;

    @Autowired
    public OrderMenuRecommenderTest(OrderMenuRecommender orderMenuRecommender, OrderMenuRepository orderMenuRepository, UserRepository userRepository, RestaurantRepository restaurantRepository, FoodMenuRepository foodMenuRepository, GroupBuyListRepository groupBuyListRepository) {
        this.orderMenuRecommender = orderMenuRecommender;
        this.orderMenuRepository = orderMenuRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
        this.foodMenuRepository = foodMenuRepository;
        this.groupBuyListRepository = groupBuyListRepository;
    }

    @Test
    void 추천메뉴_정상_작동() {
        User user = userRepository.save(UserFixture.fixture().build());
        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());
        GroupBuyList groupBuyList = groupBuyListRepository.save(GroupBuyListFixture.fixture(user, restaurant).build());

        FoodMenu foodMenu1 = foodMenuRepository.save(FoodMenuFixture.fixture(restaurant).name("1").build());
        foodMenuRepository.save(FoodMenuFixture.fixture(restaurant).name("2").build());
        foodMenuRepository.save(FoodMenuFixture.fixture(restaurant).name("3").build());

        orderMenuRepository.save(OrderMenuFixture.fixture(user, groupBuyList, foodMenu1).build());

        Long userId = user.getId();

        List<FoodMenu> list = orderMenuRecommender.recommend(userId, "testLocation");

        assertNotNull(list);
        assertEquals(3, list.size());
        assertThat(list.get(0).getName()).isBetween("1", "3");
        assertThat(list.get(1).getName()).isBetween("1", "3");
        assertThat(list.get(2).getName()).isBetween("1", "3");
    }

    @Test
    void 추천메뉴_정상_작동_5개이상() {
        User user = userRepository.save(UserFixture.fixture().build());
        userRepository.save(user);
        Long userId = user.getId();

        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu1 =  foodMenuRepository.save(FoodMenuFixture.fixture(restaurant).name("1").build());

        for(int i = 1; i < 7; i++) {
            foodMenuRepository.save(FoodMenuFixture.fixture(restaurant).name(String.valueOf(i)).build());
        }

        GroupBuyList groupBuyList = groupBuyListRepository.save(GroupBuyListFixture.fixture(user, restaurant).build());
        groupBuyListRepository.save(groupBuyList);

        orderMenuRepository.save(OrderMenuFixture.fixture(user, groupBuyList, foodMenu1).build());

        List<FoodMenu> list = orderMenuRecommender.recommend(userId, "testLocation");

        assertNotNull(list);
        assertEquals(5, list.size());
        assertThat(list.get(0).getName()).isBetween("1", "6");
        assertThat(list.get(1).getName()).isBetween("1", "6");
        assertThat(list.get(2).getName()).isBetween("1", "6");
        assertThat(list.get(3).getName()).isBetween("1", "6");
        assertThat(list.get(4).getName()).isBetween("1", "6");
    }

    @Test
    void 추천메뉴_유저_고유번호_다름() {
        User user = userRepository.save(UserFixture.fixture().build());
        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());
        GroupBuyList groupBuyList = groupBuyListRepository.save(GroupBuyListFixture.fixture(user, restaurant).build());

        FoodMenu foodMenu1 = foodMenuRepository.save(FoodMenuFixture.fixture(restaurant).name("1").build());
        foodMenuRepository.save(FoodMenuFixture.fixture(restaurant).name("2").build());
        foodMenuRepository.save(FoodMenuFixture.fixture(restaurant).name("3").build());

        orderMenuRepository.save(OrderMenuFixture.fixture(user, groupBuyList, foodMenu1).build());

        Long userId = user.getId();

        UsernameNotFoundException e = assertThrows(UsernameNotFoundException.class, () ->
                orderMenuRecommender.recommend(userId + 1L, "test")
        );

        assertEquals("Invalid user", e.getMessage());
    }

    @Test
    void 추천메뉴_유저_장소_다름() {
        User user = userRepository.save(UserFixture.fixture().build());
        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());
        GroupBuyList groupBuyList = groupBuyListRepository.save(GroupBuyListFixture.fixture(user, restaurant).build());

        FoodMenu foodMenu1 = foodMenuRepository.save(FoodMenuFixture.fixture(restaurant).name("1").build());
        foodMenuRepository.save(FoodMenuFixture.fixture(restaurant).name("2").build());
        foodMenuRepository.save(FoodMenuFixture.fixture(restaurant).name("3").build());

        OrderMenu orderMenu = OrderMenuFixture.fixture(user, groupBuyList, foodMenu1)
                .location("wrongLocation")
                .build();
        orderMenuRepository.save(orderMenu);

        Long userId = user.getId();

        List<FoodMenu> list = orderMenuRecommender.recommend(userId, "test123");

        assertTrue(list.isEmpty());
    }
}