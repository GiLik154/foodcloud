package com.example.foodcloud.domain.order.main.service.add;

import com.example.foodcloud.domain.bank.domain.BankAccount;
import com.example.foodcloud.domain.bank.domain.BankAccountRepository;
import com.example.foodcloud.domain.order.main.domain.OrderMain;
import com.example.foodcloud.domain.order.main.domain.OrderMainRepository;
import com.example.foodcloud.domain.order.main.service.add.dto.OrderMainAddServiceDto;
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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderMainAddServiceImplTest {
    private final OrderMainAddService orderMainAddService;
    private final OrderMainRepository orderMainRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public OrderMainAddServiceImplTest(OrderMainAddService OrderMainAddService,
                                       OrderMainRepository OrderMainRepository,
                                       UserRepository userRepository,
                                       RestaurantRepository restaurantRepository) {

        this.orderMainAddService = OrderMainAddService;
        this.orderMainRepository = OrderMainRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Test
    void ??????_??????_??????_???????????????() {
        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);
        Long restaurantId = restaurant.getId();

        OrderMainAddServiceDto OrderMainAddServiceDto = new OrderMainAddServiceDto("test", restaurantId);
        orderMainAddService.add(userId, OrderMainAddServiceDto);
        OrderMain orderMain = orderMainRepository.findByUserId(userId).get(0);

        assertEquals("test", orderMain.getLocation());
        assertEquals("Payment waiting", orderMain.getResult());
        assertEquals(user, orderMain.getUser());
        assertEquals(restaurant, orderMain.getRestaurant());
        assertNotNull(orderMain.getTime());
    }

    @Test
    void ??????_??????_??????_??????????????????_??????() {
        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);
        Long restaurantId = restaurantRepository.findByUserId(userId).get(0).getId();

        OrderMainAddServiceDto OrderMainAddServiceDto = new OrderMainAddServiceDto("test", restaurantId);

        UsernameNotFoundException e = assertThrows(UsernameNotFoundException.class, () ->
                orderMainAddService.add(userId + 1L, OrderMainAddServiceDto)
        );
    }

    @Test
    void ??????_??????_??????_??????????????????_??????() {
        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);
        Long restaurantId = restaurantRepository.findByUserId(userId).get(0).getId();

        OrderMainAddServiceDto OrderMainAddServiceDto = new OrderMainAddServiceDto("test", restaurantId + 1L);

        NotFoundRestaurantException e = assertThrows(NotFoundRestaurantException.class, () ->
                orderMainAddService.add(userId, OrderMainAddServiceDto)
        );
        assertEquals("Not found Restaurant", e.getMessage());
    }
}