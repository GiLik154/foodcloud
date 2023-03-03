package com.example.foodcloud.domain.ordermenu.service.add;

import com.example.foodcloud.domain.bank.domain.BankAccount;
import com.example.foodcloud.domain.bank.domain.BankAccountRepository;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenuRepository;
import com.example.foodcloud.domain.ordermain.domain.OrderMain;
import com.example.foodcloud.domain.ordermain.domain.OrderMainRepository;
import com.example.foodcloud.domain.ordermenu.domain.OrderMenu;
import com.example.foodcloud.domain.ordermenu.domain.OrderMenuRepository;
import com.example.foodcloud.domain.ordermenu.service.dto.OrderMenuDto;
import com.example.foodcloud.domain.restaurant.domain.Restaurant;
import com.example.foodcloud.domain.restaurant.domain.RestaurantRepository;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import com.example.foodcloud.exception.NotFoundBankAccountException;
import com.example.foodcloud.exception.NotFoundFoodMenuException;
import com.example.foodcloud.exception.NotFoundOrderMainException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderMenuAddServiceImplTest {
    private final OrderMenuAddService orderMenuAddService;
    private final OrderMenuRepository orderMenuRepository;
    private final UserRepository userRepository;
    private final BankAccountRepository bankAccountRepository;
    private final RestaurantRepository restaurantRepository;
    private final FoodMenuRepository foodMenuRepository;
    private final OrderMainRepository orderMainRepository;

    @Autowired
    OrderMenuAddServiceImplTest(OrderMenuAddService orderMenuAddService, OrderMenuRepository orderMenuRepository, UserRepository userRepository, BankAccountRepository bankAccountRepository, RestaurantRepository restaurantRepository, FoodMenuRepository foodMenuRepository, OrderMainRepository orderMainRepository) {
        this.orderMenuAddService = orderMenuAddService;
        this.orderMenuRepository = orderMenuRepository;
        this.userRepository = userRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.restaurantRepository = restaurantRepository;
        this.foodMenuRepository = foodMenuRepository;
        this.orderMainRepository = orderMainRepository;
    }

    @Test
    void 주문메뉴_추가_정상작동() {
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

        OrderMenuDto orderMenuDto = new OrderMenuDto("test", 5, bankAccount.getId(), foodMenu.getId(), orderMain.getId());
        orderMenuAddService.add(userId, orderMenuDto);

        OrderMenu orderMenu = orderMenuRepository.findAll().get(0);

        assertEquals("test", orderMenu.getLocation());
        assertEquals(5, orderMenu.getCount());
        assertEquals(user, orderMenu.getUser());
        assertEquals(bankAccount, orderMenu.getBankAccount());
        assertEquals(foodMenu, orderMenu.getFoodMenu());
        assertEquals(orderMain, orderMenu.getOrderMain());
        assertEquals(25000, orderMenu.getPrice());
        assertEquals("Received", orderMenu.getResult());
    }

    @Test
    void 주문메뉴_추가_유저고유번호_다름() {
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

        OrderMenuDto orderMenuDto = new OrderMenuDto("test", 5, bankAccount.getId(), foodMenu.getId(), orderMain.getId());
        orderMenuAddService.add(user.getId(), orderMenuDto);

        UsernameNotFoundException e = assertThrows(UsernameNotFoundException.class, () ->
                orderMenuAddService.add(userId + 1L, orderMenuDto)
        );

    }

    @Test
    void 주문메뉴_추가_계좌_고유번호_다름() {
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

        OrderMenuDto orderMenuDto = new OrderMenuDto("test", 5, bankAccount.getId() + 1L, foodMenu.getId(), orderMain.getId());

        NotFoundBankAccountException e = assertThrows(NotFoundBankAccountException.class, () ->
                orderMenuAddService.add(userId, orderMenuDto)
        );

        assertEquals("Not found BankAccount", e.getMessage());
    }

    @Test
    void 주문메뉴_추가_음식메뉴_고유번호_다름() {
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

        OrderMenuDto orderMenuDto = new OrderMenuDto("test", 5, bankAccount.getId(), foodMenu.getId() + 1L, orderMain.getId());

        NotFoundFoodMenuException e = assertThrows(NotFoundFoodMenuException.class, () ->
                orderMenuAddService.add(userId, orderMenuDto)
        );

        assertEquals("Not found FoodMenu", e.getMessage());
    }

    @Test
    void 주문메뉴_추가_오더메인_고유번호_다름() {
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

        OrderMenuDto orderMenuDto = new OrderMenuDto("test", 5, bankAccount.getId(), foodMenu.getId(), orderMain.getId() + 1L);

        NotFoundOrderMainException e = assertThrows(NotFoundOrderMainException.class, () ->
                orderMenuAddService.add(userId, orderMenuDto)
        );



        assertEquals("Not found OrderMain", e.getMessage());
    }
}