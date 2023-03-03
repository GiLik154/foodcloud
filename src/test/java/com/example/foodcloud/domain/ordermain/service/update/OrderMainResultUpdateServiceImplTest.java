package com.example.foodcloud.domain.ordermain.service.update;

import com.example.foodcloud.domain.bank.domain.BankAccount;
import com.example.foodcloud.domain.bank.domain.BankAccountRepository;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenuRepository;
import com.example.foodcloud.domain.ordermain.domain.OrderMain;
import com.example.foodcloud.domain.ordermain.domain.OrderMainRepository;
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
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderMainResultUpdateServiceImplTest {
    private final OrderMainResultUpdateService orderMainResultUpdateService;
    private final OrderMainRepository orderMainRepository;
    private final UserRepository userRepository;
    private final BankAccountRepository bankAccountRepository;
    private final RestaurantRepository restaurantRepository;
    private final OrderMenuRepository orderMenuRepository;
    private final FoodMenuRepository foodMenuRepository;

    @Autowired
    public OrderMainResultUpdateServiceImplTest(OrderMainResultUpdateService OrderMainResultUpdateService, OrderMainRepository OrderMainRepository, UserRepository userRepository, BankAccountRepository bankAccountRepository, RestaurantRepository restaurantRepository, OrderMenuRepository orderMenuRepository, FoodMenuRepository foodMenuRepository) {
        this.orderMainResultUpdateService = OrderMainResultUpdateService;
        this.orderMainRepository = OrderMainRepository;
        this.userRepository = userRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.restaurantRepository = restaurantRepository;
        this.orderMenuRepository = orderMenuRepository;
        this.foodMenuRepository = foodMenuRepository;
    }

    @Test
    void 메인주문_업데이트_정상_작동() {
        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();

        BankAccount bankAccount = new BankAccount("test", "test", "001", user);
        bankAccountRepository.save(bankAccount);

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);

        OrderMain orderMain = new OrderMain("test", "test", user, bankAccount, restaurant);
        orderMainRepository.save(orderMain);

        boolean isUpdate = orderMainResultUpdateService.update(userId, orderMain.getId(), "PREPARED");

        assertTrue(isUpdate);
        assertEquals("Prepared", orderMain.getResult());
    }

    @Test
    void 메인주문_업데이트_유저고유번호_다름() {
        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();

        BankAccount bankAccount = new BankAccount("test", "test", "001", user);
        bankAccountRepository.save(bankAccount);

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);

        OrderMain orderMain = new OrderMain("test", "test", user, bankAccount, restaurant);
        orderMainRepository.save(orderMain);

        boolean isUpdate = orderMainResultUpdateService.update(userId + 1L, orderMain.getId(), "PREPARED");

        assertFalse(isUpdate);
        assertEquals("Received", orderMain.getResult());
    }

    @Test
    void 메인주문_업데이트_오더고유번호_다름() {
        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();

        BankAccount bankAccount = new BankAccount("test", "test", "001", user);
        bankAccountRepository.save(bankAccount);

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);

        OrderMain orderMain = new OrderMain("test", "test", user, bankAccount, restaurant);
        orderMainRepository.save(orderMain);

        boolean isUpdate = orderMainResultUpdateService.update(userId, orderMain.getId() + 1L, "PREPARED");

        assertFalse(isUpdate);
        assertEquals("Received", orderMain.getResult());
    }

    @Test
    void 메인주문_업데이트_주문내역_변경() {
        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();

        BankAccount bankAccount = new BankAccount("test", "test", "001", user);
        bankAccountRepository.save(bankAccount);

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);

        OrderMain orderMain = new OrderMain("test", "test", user, bankAccount, restaurant);
        orderMainRepository.save(orderMain);

        FoodMenu foodMenu = new FoodMenu("test", 5000, "test", "test", "test", "test", restaurant);
        foodMenuRepository.save(foodMenu);

        OrderMenu orderMenu = new OrderMenu("tset", 5, "test", user, bankAccount, foodMenu, orderMain);
        orderMenuRepository.save(orderMenu);

        boolean isUpdate = orderMainResultUpdateService.update(userId, orderMain.getId(), "PREPARED");

        assertTrue(isUpdate);
        assertEquals("Prepared", orderMenu.getResult());
    }
}