package com.example.foodcloud.domain.restaurant.service.update;

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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RestaurantCountUpdaterTest {
    private final RestaurantCountUpdater restaurantCountUpdater;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    @Autowired
    public RestaurantCountUpdaterTest(RestaurantCountUpdater restaurantCountUpdater, RestaurantRepository restaurantRepository, UserRepository userRepository) {
        this.restaurantCountUpdater = restaurantCountUpdater;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    @Test
    void 식당의_주문횟수_동시성_테스트() throws InterruptedException {
        User user = new User("test", "test", "test");
        userRepository.save(user);

        Restaurant restaurant = new Restaurant("testName", "testLocation", "testHours", user);
        restaurantRepository.save(restaurant);
        Long restaurantId = restaurant.getId();

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CountDownLatch countDownLatch = new CountDownLatch(100);

        for (int i = 0; i < 100; i++) {
            executorService.execute(() -> {
                restaurantCountUpdater.increaseOrderCount(restaurantId);
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();

        Restaurant updateRestaurant = restaurantRepository.findById(restaurantId).get();

        try {
            assertEquals(100, updateRestaurant.getOrderCount());
        } finally {
            restaurantRepository.delete(restaurant);
            userRepository.delete(user);
        }
    }
}