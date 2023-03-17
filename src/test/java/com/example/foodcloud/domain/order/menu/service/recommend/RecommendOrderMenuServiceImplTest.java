package com.example.foodcloud.domain.order.menu.service.recommend;

import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenuRepository;
import com.example.foodcloud.domain.order.main.domain.OrderMain;
import com.example.foodcloud.domain.order.main.domain.OrderMainRepository;
import com.example.foodcloud.domain.order.menu.domain.OrderMenu;
import com.example.foodcloud.domain.order.menu.domain.OrderMenuRepository;
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
class RecommendOrderMenuServiceImplTest {
    private final RecommendOrderMenuService recommendOrderMenuService;
    private final OrderMenuRepository orderMenuRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final FoodMenuRepository foodMenuRepository;
    private final OrderMainRepository orderMainRepository;

    @Autowired
    public RecommendOrderMenuServiceImplTest(RecommendOrderMenuService recommendOrderMenuService, OrderMenuRepository orderMenuRepository, UserRepository userRepository, RestaurantRepository restaurantRepository, FoodMenuRepository foodMenuRepository, OrderMainRepository orderMainRepository) {
        this.recommendOrderMenuService = recommendOrderMenuService;
        this.orderMenuRepository = orderMenuRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
        this.foodMenuRepository = foodMenuRepository;
        this.orderMainRepository = orderMainRepository;
    }

    @Test
    void 추천메뉴_정상_작동() {
        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu1 = new FoodMenu("1", 5000, "test", "test", "test", "test", restaurant);
        FoodMenu foodMenu2 = new FoodMenu("2", 5000, "test", "test", "test", "test", restaurant);
        FoodMenu foodMenu3 = new FoodMenu("3", 5000, "test", "test", "test", "test", restaurant);
        foodMenuRepository.save(foodMenu1);
        foodMenuRepository.save(foodMenu2);
        foodMenuRepository.save(foodMenu3);

        OrderMain orderMain = new OrderMain("test", "test", user, restaurant);
        orderMainRepository.save(orderMain);

        OrderMenu orderMenu = new OrderMenu("test1", 5, "test", user, foodMenu1, orderMain);
        orderMenuRepository.save(orderMenu);

        List<FoodMenu> list = recommendOrderMenuService.recommend(userId, "test");

        assertNotNull(list);
        assertEquals(3, list.size());
        assertThat(list.get(0).getFoodMenuName()).isBetween("1", "3");
        assertThat(list.get(1).getFoodMenuName()).isBetween("1", "3");
        assertThat(list.get(2).getFoodMenuName()).isBetween("1", "3");
    }

    @Test
    void 추천메뉴_정상_작동_5개이상() {
        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu1 = new FoodMenu("1", 5000, "test", "test", "test", "test", restaurant);
        FoodMenu foodMenu2 = new FoodMenu("2", 5000, "test", "test", "test", "test", restaurant);
        FoodMenu foodMenu3 = new FoodMenu("3", 5000, "test", "test", "test", "test", restaurant);
        FoodMenu foodMenu4 = new FoodMenu("4", 5000, "test", "test", "test", "test", restaurant);
        FoodMenu foodMenu5 = new FoodMenu("5", 5000, "test", "test", "test", "test", restaurant);
        FoodMenu foodMenu6 = new FoodMenu("6", 5000, "test", "test", "test", "test", restaurant);
        foodMenuRepository.save(foodMenu1);
        foodMenuRepository.save(foodMenu2);
        foodMenuRepository.save(foodMenu3);
        foodMenuRepository.save(foodMenu4);
        foodMenuRepository.save(foodMenu5);
        foodMenuRepository.save(foodMenu6);

        OrderMain orderMain = new OrderMain("test", "test", user, restaurant);
        orderMainRepository.save(orderMain);

        OrderMenu orderMenu = new OrderMenu("test1", 5, "test", user, foodMenu1, orderMain);
        orderMenuRepository.save(orderMenu);

        List<FoodMenu> list = recommendOrderMenuService.recommend(userId, "test");

        assertNotNull(list);
        assertEquals(5, list.size());
        assertThat(list.get(0).getFoodMenuName()).isBetween("1", "6");
        assertThat(list.get(1).getFoodMenuName()).isBetween("1", "6");
        assertThat(list.get(2).getFoodMenuName()).isBetween("1", "6");
        assertThat(list.get(3).getFoodMenuName()).isBetween("1", "6");
        assertThat(list.get(4).getFoodMenuName()).isBetween("1", "6");
    }

    @Test
    void 추천메뉴_유저_고유번호_다름() {
        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu1 = new FoodMenu("1", 5000, "test", "test", "test", "test", restaurant);
        FoodMenu foodMenu2 = new FoodMenu("2", 5000, "test", "test", "test", "test", restaurant);
        FoodMenu foodMenu3 = new FoodMenu("3", 5000, "test", "test", "test", "test", restaurant);
        foodMenuRepository.save(foodMenu1);
        foodMenuRepository.save(foodMenu2);
        foodMenuRepository.save(foodMenu3);

        OrderMain orderMain = new OrderMain("test", "test", user, restaurant);
        orderMainRepository.save(orderMain);

        OrderMenu orderMenu = new OrderMenu("test1", 5, "test", user, foodMenu1, orderMain);
        orderMenuRepository.save(orderMenu);

        UsernameNotFoundException e = assertThrows(UsernameNotFoundException.class, () ->
                recommendOrderMenuService.recommend(userId + 1L, "test")
        );

        assertEquals("Invalid user", e.getMessage());
    }

    @Test
    void 추천메뉴_유저_장소_다름() {
        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu1 = new FoodMenu("1", 5000, "test", "test", "test", "test", restaurant);
        FoodMenu foodMenu2 = new FoodMenu("2", 5000, "test", "test", "test", "test", restaurant);
        FoodMenu foodMenu3 = new FoodMenu("3", 5000, "test", "test", "test", "test", restaurant);
        foodMenuRepository.save(foodMenu1);
        foodMenuRepository.save(foodMenu2);
        foodMenuRepository.save(foodMenu3);

        OrderMain orderMain = new OrderMain("test", "test", user, restaurant);
        orderMainRepository.save(orderMain);

        OrderMenu orderMenu = new OrderMenu("test1", 5, "test", user, foodMenu1, orderMain);
        orderMenuRepository.save(orderMenu);

        List<FoodMenu> list = recommendOrderMenuService.recommend(userId, "test123");

        assertTrue(list.isEmpty());
    }
}