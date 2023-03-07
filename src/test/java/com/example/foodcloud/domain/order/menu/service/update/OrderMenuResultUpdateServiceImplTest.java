package com.example.foodcloud.domain.order.menu.service.update;

import com.example.foodcloud.domain.bank.domain.BankAccount;
import com.example.foodcloud.domain.bank.domain.BankAccountRepository;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenuRepository;
import com.example.foodcloud.domain.order.main.domain.OrderMain;
import com.example.foodcloud.domain.order.main.domain.OrderMainRepository;
import com.example.foodcloud.domain.order.menu.domain.OrderMenu;
import com.example.foodcloud.domain.order.menu.domain.OrderMenuRepository;
import com.example.foodcloud.domain.order.menu.service.add.OrderMenuAddService;
import com.example.foodcloud.domain.order.menu.service.dto.OrderMenuDto;
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
class OrderMenuResultUpdateServiceImplTest {
    private final OrderMenuResultUpdateService orderMenuResultUpdateService;
    private final OrderMenuRepository orderMenuRepository;
    private final UserRepository userRepository;
    private final BankAccountRepository bankAccountRepository;
    private final RestaurantRepository restaurantRepository;
    private final FoodMenuRepository foodMenuRepository;
    private final OrderMainRepository orderMainRepository;

    @Autowired
    public OrderMenuResultUpdateServiceImplTest(OrderMenuResultUpdateService orderMenuResultUpdateService, OrderMenuRepository orderMenuRepository, UserRepository userRepository, BankAccountRepository bankAccountRepository, RestaurantRepository restaurantRepository, FoodMenuRepository foodMenuRepository, OrderMainRepository orderMainRepository) {
        this.orderMenuResultUpdateService = orderMenuResultUpdateService;
        this.orderMenuRepository = orderMenuRepository;
        this.userRepository = userRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.restaurantRepository = restaurantRepository;
        this.foodMenuRepository = foodMenuRepository;
        this.orderMainRepository = orderMainRepository;
    }

    @Test
    void 오더메뉴_업데이트_정상_작동() {
        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();

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
        Long orderMenuId = orderMenu.getId();

        boolean isUpdate = orderMenuResultUpdateService.update(userId, orderMenuId, "COOKING");

        assertTrue(isUpdate);
        assertEquals("Cooking", orderMenu.getResult());
    }

    @Test
    void 오더메뉴_업데이트_유저_고유번호_다름() {
        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();

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
        Long orderMenuId = orderMenu.getId();

        boolean isUpdate = orderMenuResultUpdateService.update(userId + 1L, orderMenuId, "COOKING");

        assertFalse(isUpdate);
        assertEquals("Received", orderMenu.getResult());
    }

    @Test
    void 오더메뉴_업데이트_오더메뉴_고유번호_다름() {
        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();

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
        Long orderMenuId = orderMenu.getId();

        boolean isUpdate = orderMenuResultUpdateService.update(userId, orderMenuId +1L, "COOKING");

        assertFalse(isUpdate);
        assertEquals("Received", orderMenu.getResult());
    }
}