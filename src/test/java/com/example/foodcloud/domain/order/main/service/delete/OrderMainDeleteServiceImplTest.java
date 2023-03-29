package com.example.foodcloud.domain.order.main.service.delete;

import com.example.foodcloud.domain.order.main.domain.OrderMain;
import com.example.foodcloud.domain.order.main.domain.OrderMainRepository;
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
class OrderMainDeleteServiceImplTest {
    private final OrderMainDeleteService orderMainDeleteService;
    private final OrderMainRepository orderMainRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public OrderMainDeleteServiceImplTest(OrderMainDeleteService orderMainDeleteService,
                                          OrderMainRepository orderMainRepository,
                                          UserRepository userRepository,
                                          RestaurantRepository restaurantRepository) {
        this.orderMainDeleteService = orderMainDeleteService;
        this.orderMainRepository = orderMainRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Test
    void 오더메인_삭제_정상작동() {
        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();


        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);

        OrderMain orderMain = new OrderMain("test", "test", user, restaurant);
        orderMainRepository.save(orderMain);

        boolean isDelete = orderMainDeleteService.delete(userId, orderMain.getId());

        assertTrue(isDelete);
        assertFalse(orderMainRepository.existsById(orderMain.getId()));
    }

    @Test
    void 오더메인_삭제_유저_고유번호_다름() {
        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);

        OrderMain orderMain = new OrderMain("test", "test", user, restaurant);
        orderMainRepository.save(orderMain);

        boolean isDelete = orderMainDeleteService.delete(userId + 1L, orderMain.getId());

        assertFalse(isDelete);
        assertTrue(orderMainRepository.existsById(orderMain.getId()));
    }

    @Test
    void 오더메인_삭제_오더메인_고유번호_다름() {
        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);

        OrderMain orderMain = new OrderMain("test", "test", user, restaurant);
        orderMainRepository.save(orderMain);

        boolean isDelete = orderMainDeleteService.delete(userId, orderMain.getId() + 1L);

        assertFalse(isDelete);
        assertTrue(orderMainRepository.existsById(orderMain.getId()));
    }
}