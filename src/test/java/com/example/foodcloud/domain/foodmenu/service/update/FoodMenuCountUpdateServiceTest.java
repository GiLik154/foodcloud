package com.example.foodcloud.domain.foodmenu.service.update;

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

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FoodMenuCountUpdateServiceTest {
    private final FoodMenuCountUpdateService foodMenuCountUpdateService;
    private final OrderMenuRepository orderMenuRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final FoodMenuRepository foodMenuRepository;
    private final GroupBuyListRepository groupBuyListRepository;

    @Autowired
    public FoodMenuCountUpdateServiceTest(FoodMenuCountUpdateService foodMenuCountUpdateService, FoodMenuRepository foodMenuRepository, RestaurantRepository restaurantRepository, OrderMenuRepository orderMenuRepository, UserRepository userRepository, GroupBuyListRepository groupBuyListRepository) {
        this.foodMenuCountUpdateService = foodMenuCountUpdateService;
        this.foodMenuRepository = foodMenuRepository;
        this.restaurantRepository = restaurantRepository;
        this.orderMenuRepository = orderMenuRepository;
        this.userRepository = userRepository;
        this.groupBuyListRepository = groupBuyListRepository;
    }

    @Test
    void 푸드메뉴_업데이트_동시성_테스트() throws InterruptedException {
        User user = userRepository.save(UserFixture.fixture().build());
        userRepository.save(user);

        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = foodMenuRepository.save(FoodMenuFixture.fixture(restaurant).build());
        foodMenuRepository.save(foodMenu);
        Long foodMenuId = foodMenu.getId();

        GroupBuyList groupBuyList = groupBuyListRepository.save(GroupBuyListFixture.fixture(user, restaurant).build());
        groupBuyListRepository.save(groupBuyList);

        OrderMenu orderMenu = orderMenuRepository.save(OrderMenuFixture.fixture(user, groupBuyList, foodMenu).build());
        orderMenuRepository.save(orderMenu);

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CountDownLatch countDownLatch = new CountDownLatch(100);

        for (int i = 0; i < 100; i++) {
            executorService.execute(() -> {
                foodMenuCountUpdateService.increaseOrderCount(foodMenuId);
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();

        try {
            assertEquals(100, foodMenuRepository.findById(foodMenu.getId()).get().getOrderCount());
        } finally {
            orderMenuRepository.delete(orderMenu);
            groupBuyListRepository.delete(groupBuyList);
            foodMenuRepository.delete(foodMenu);
            restaurantRepository.delete(restaurant);
            userRepository.delete(user);
        }
    }
}