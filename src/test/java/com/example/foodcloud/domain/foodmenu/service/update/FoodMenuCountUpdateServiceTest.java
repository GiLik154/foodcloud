package com.example.foodcloud.domain.foodmenu.service.update;

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
import com.example.foodcloud.enums.foodmenu.FoodTypes;
import com.example.foodcloud.enums.foodmenu.MeatTypes;
import com.example.foodcloud.enums.foodmenu.Temperature;
import com.example.foodcloud.enums.foodmenu.Vegetables;
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
        User user = new User("test", "test", "test");
        userRepository.save(user);

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = new FoodMenu("test", 5000, Temperature.COLD, FoodTypes.ADE, MeatTypes.CHICKEN, Vegetables.FEW, restaurant);
        foodMenuRepository.save(foodMenu);
        Long foodMenuId = foodMenu.getId();

        GroupBuyList groupBuyList = new GroupBuyList("test", "test", user, restaurant);
        groupBuyListRepository.save(groupBuyList);

        OrderMenu orderMenu = new OrderMenu("test", 5, "test", user, foodMenu, groupBuyList);
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