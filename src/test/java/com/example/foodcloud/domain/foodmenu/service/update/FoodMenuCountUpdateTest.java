package com.example.foodcloud.domain.foodmenu.service.update;

import com.example.foodcloud.domain.bank.domain.BankAccount;
import com.example.foodcloud.domain.bank.domain.BankAccountRepository;
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
import org.springframework.transaction.annotation.Transactional;

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
    private final BankAccountRepository bankAccountRepository;
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
        this.bankAccountRepository = bankAccountRepository;
        this.orderMainRepository = orderMainRepository;
    }

    @Test
    void name() throws InterruptedException {
        User user = new User("test", "test", "test");
        userRepository.save(user);

        BankAccount bankAccount = new BankAccount("test", "test", "001", user);
        bankAccountRepository.save(bankAccount);

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = new FoodMenu("test", 5000, "test", "test", "test", "test", restaurant);
        foodMenuRepository.save(foodMenu);

        OrderMain orderMain = new OrderMain("test", "test", user, bankAccount, restaurant);
        orderMainRepository.save(orderMain);

        OrderMenu orderMenu = new OrderMenu("test", 5, "test", user, bankAccount, foodMenu, orderMain);
        orderMenuRepository.save(orderMenu);

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        CountDownLatch countDownLatch = new CountDownLatch(100);

        for (int i = 0; i < 100; i++) {
            executorService.execute(() -> {
                foodMenuUpdateService.updateFoodMenuOrderCount(foodMenu, orderMenu, 87L);
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();

        assertEquals(100, foodMenuRepository.findById(87L).get().getOrderCount());

    }
}