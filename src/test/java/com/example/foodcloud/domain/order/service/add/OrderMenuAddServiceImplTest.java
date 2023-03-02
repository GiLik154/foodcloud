package com.example.foodcloud.domain.order.service.add;

import com.example.foodcloud.domain.bank.domain.BankAccount;
import com.example.foodcloud.domain.bank.domain.BankAccountRepository;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenuRepository;
import com.example.foodcloud.domain.order.domain.OrderMenu;
import com.example.foodcloud.domain.order.domain.OrderRepository;
import com.example.foodcloud.domain.order.service.dto.OrderMenuDto;
import com.example.foodcloud.domain.restaurant.domain.Restaurant;
import com.example.foodcloud.domain.restaurant.domain.RestaurantRepository;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderMenuAddServiceImplTest {
    private final OrderMenuAddService orderMenuAddService;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final BankAccountRepository bankAccountRepository;
    private final RestaurantRepository restaurantRepository;
    private final FoodMenuRepository foodMenuRepository;

    @Autowired
    public OrderMenuAddServiceImplTest(OrderMenuAddService orderMenuAddService,
                                       OrderRepository orderRepository,
                                       UserRepository userRepository,
                                       BankAccountRepository bankAccountRepository,
                                       RestaurantRepository restaurantRepository,
                                       FoodMenuRepository foodMenuRepository) {

        this.orderMenuAddService = orderMenuAddService;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.restaurantRepository = restaurantRepository;
        this.foodMenuRepository = foodMenuRepository;
    }

    @Test
    void name() {
        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();

        BankAccount bankAccount = new BankAccount("test", "test", "001", user);
        bankAccountRepository.save(bankAccount);
        Long bankAccountId = bankAccountRepository.findBankAccountByUserId(userId).get(0).getId();

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);
        Long restaurantId = restaurantRepository.findByUserId(userId).get(0).getId();

        FoodMenu foodMenu = new FoodMenu("test", 5000, "test", "test", "test", "test", restaurant);
        foodMenuRepository.save(foodMenu);
        Long foodMenuId = foodMenu.getId();

        OrderMenuDto orderMenuDto = new OrderMenuDto("test", bankAccountId, restaurantId, foodMenuId);
        orderMenuAddService.add(userId, orderMenuDto);
        OrderMenu orderMenu = orderRepository.findByUserID(userId).get(0);

        assertEquals("test", orderMenu.getLocation());
        assertEquals("In progress", orderMenu.getResult());
        assertEquals(userId, orderMenu.getUser());
        assertEquals(bankAccountId, orderMenu.getBankAccount());
        assertEquals(restaurantId, orderMenu.getRestaurant());
        assertEquals(foodMenuId, orderMenu.getFoodMenu());
    }
}