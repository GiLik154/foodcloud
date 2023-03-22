package com.example.foodcloud.domain.foodmenu.service.update;

import com.example.foodcloud.domain.payment.bank.domain.BankAccountRepository;
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

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FoodMenuCountUpdateTest {
    private final FoodMenuUpdateService foodMenuUpdateService;
    private final OrderMenuRepository orderMenuRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final FoodMenuRepository foodMenuRepository;
    private final OrderMainRepository orderMainRepository;

    @Autowired
    public FoodMenuCountUpdateTest(FoodMenuUpdateService foodMenuUpdateService, FoodMenuRepository foodMenuRepository, RestaurantRepository restaurantRepository, OrderMenuRepository orderMenuRepository, UserRepository userRepository, BankAccountRepository bankAccountRepository, OrderMainRepository orderMainRepository) {
        this.foodMenuUpdateService = foodMenuUpdateService;
        this.foodMenuRepository = foodMenuRepository;
        this.restaurantRepository = restaurantRepository;
        this.orderMenuRepository = orderMenuRepository;
        this.userRepository = userRepository;
        this.orderMainRepository = orderMainRepository;
    }

    @Test
    void 푸드메뉴_업데이트_동시성_테스트() throws InterruptedException {
        User user = new User("test", "test", "test");
        userRepository.save(user);

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = new FoodMenu("test", 5000, "test", "test", "test", "test", restaurant);
        foodMenuRepository.save(foodMenu);
        Long foodMenuId = foodMenu.getId();

        OrderMain orderMain = new OrderMain("test", "test", user,  restaurant);
        orderMainRepository.save(orderMain);

        OrderMenu orderMenu = new OrderMenu("test", 5, "test", user,  foodMenu, orderMain);
        orderMenuRepository.save(orderMenu);

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        CountDownLatch countDownLatch = new CountDownLatch(100);

        for (int i = 0; i < 100; i++) {
            executorService.execute(() -> {
                foodMenuUpdateService.updateFoodMenuOrderCount(foodMenuId);
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();

        assertEquals(100, foodMenuRepository.findById(foodMenu.getId()).get().getOrderCount());

        orderMenuRepository.delete(orderMenu);
        orderMainRepository.delete(orderMain);
        foodMenuRepository.delete(foodMenu);
        restaurantRepository.delete(restaurant);
        userRepository.delete(user);
    }
}