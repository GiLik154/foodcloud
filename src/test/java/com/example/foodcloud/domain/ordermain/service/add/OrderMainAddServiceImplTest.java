package com.example.foodcloud.domain.ordermain.service.add;

import com.example.foodcloud.domain.bank.domain.BankAccount;
import com.example.foodcloud.domain.bank.domain.BankAccountRepository;
import com.example.foodcloud.domain.ordermain.domain.OrderMain;
import com.example.foodcloud.domain.ordermain.domain.OrderMainRepository;
import com.example.foodcloud.domain.ordermain.service.dto.OrderMainDto;
import com.example.foodcloud.domain.restaurant.domain.Restaurant;
import com.example.foodcloud.domain.restaurant.domain.RestaurantRepository;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import com.example.foodcloud.exception.NotFoundBankAccountException;
import com.example.foodcloud.exception.NotFoundRestaurantException;
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
class OrderMainAddServiceImplTest {
    private final OrderMainAddService orderMainAddService;
    private final OrderMainRepository orderMainRepository;
    private final UserRepository userRepository;
    private final BankAccountRepository bankAccountRepository;
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public OrderMainAddServiceImplTest(OrderMainAddService OrderMainAddService,
                                       OrderMainRepository OrderMainRepository,
                                       UserRepository userRepository,
                                       BankAccountRepository bankAccountRepository,
                                       RestaurantRepository restaurantRepository) {

        this.orderMainAddService = OrderMainAddService;
        this.orderMainRepository = OrderMainRepository;
        this.userRepository = userRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Test
    void 오더_메뉴_추가_정삭상작동() {
        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();

        BankAccount bankAccount = new BankAccount("test", "test", "001", user);
        bankAccountRepository.save(bankAccount);
        Long bankAccountId = bankAccount.getId();

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);
        Long restaurantId = restaurant.getId();

        OrderMainDto OrderMainDto = new OrderMainDto("test", bankAccountId, restaurantId, "RECEIVED");
        orderMainAddService.add(userId, OrderMainDto);
        OrderMain orderMain = orderMainRepository.findByUserId(userId).get(0);

        assertEquals("test", orderMain.getLocation());
        assertEquals("Received", orderMain.getResult());
        assertEquals(user, orderMain.getUser());
        assertEquals(bankAccount, orderMain.getBankAccount());
        assertEquals(restaurant, orderMain.getRestaurant());
    }

    @Test
    void 오더_메뉴_추가_유저고유번호_다름() {
        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();

        BankAccount bankAccount = new BankAccount("test", "test", "001", user);
        bankAccountRepository.save(bankAccount);
        Long bankAccountId = bankAccountRepository.findBankAccountByUserId(userId).get(0).getId();

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);
        Long restaurantId = restaurantRepository.findByUserId(userId).get(0).getId();

        OrderMainDto OrderMainDto = new OrderMainDto("test", bankAccountId, restaurantId, "RECEIVED");

        UsernameNotFoundException e = assertThrows(UsernameNotFoundException.class, () ->
                orderMainAddService.add(userId + 1L, OrderMainDto)
        );

    }

    @Test
    void 오더_메뉴_추가_계좌고유번호_다름() {
        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();

        BankAccount bankAccount = new BankAccount("test", "test", "001", user);
        bankAccountRepository.save(bankAccount);
        Long bankAccountId = bankAccountRepository.findBankAccountByUserId(userId).get(0).getId();

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);
        Long restaurantId = restaurantRepository.findByUserId(userId).get(0).getId();

        OrderMainDto OrderMainDto = new OrderMainDto("test", bankAccountId + 1L, restaurantId, "RECEIVED");

        NotFoundBankAccountException e = assertThrows(NotFoundBankAccountException.class, () ->
                orderMainAddService.add(userId, OrderMainDto)
        );

        assertEquals("Not found BankAccount", e.getMessage());
    }

    @Test
    void 오더_메뉴_추가_식당고유번호_다름() {
        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();

        BankAccount bankAccount = new BankAccount("test", "test", "001", user);
        bankAccountRepository.save(bankAccount);
        Long bankAccountId = bankAccountRepository.findBankAccountByUserId(userId).get(0).getId();

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);
        Long restaurantId = restaurantRepository.findByUserId(userId).get(0).getId();

        OrderMainDto OrderMainDto = new OrderMainDto("test", bankAccountId, restaurantId + 1L, "RECEIVED");

        NotFoundRestaurantException e = assertThrows(NotFoundRestaurantException.class, () ->
                orderMainAddService.add(userId, OrderMainDto)
        );

        assertEquals("Not found Restaurant", e.getMessage());
    }
}