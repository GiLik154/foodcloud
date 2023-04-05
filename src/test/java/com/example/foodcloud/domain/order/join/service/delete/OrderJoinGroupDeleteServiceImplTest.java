package com.example.foodcloud.domain.order.join.service.delete;

import com.example.foodcloud.domain.order.join.domain.OrderJoinGroup;
import com.example.foodcloud.domain.order.join.domain.OrderJoinGroupRepository;
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
class OrderJoinGroupDeleteServiceImplTest {
    private final OrderJoinGroupDeleteService orderJoinGroupDeleteService;
    private final OrderJoinGroupRepository orderJoinGroupRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public OrderJoinGroupDeleteServiceImplTest(OrderJoinGroupDeleteService orderJoinGroupDeleteService,
                                               OrderJoinGroupRepository orderJoinGroupRepository,
                                               UserRepository userRepository,
                                               RestaurantRepository restaurantRepository) {
        this.orderJoinGroupDeleteService = orderJoinGroupDeleteService;
        this.orderJoinGroupRepository = orderJoinGroupRepository;
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

        OrderJoinGroup orderJoinGroup = new OrderJoinGroup("test", "test", user, restaurant);
        orderJoinGroupRepository.save(orderJoinGroup);

        boolean isDelete = orderJoinGroupDeleteService.delete(userId, orderJoinGroup.getId());

        assertTrue(isDelete);
        assertFalse(orderJoinGroupRepository.existsById(orderJoinGroup.getId()));
    }

    @Test
    void 오더메인_삭제_유저_고유번호_다름() {
        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);

        OrderJoinGroup orderJoinGroup = new OrderJoinGroup("test", "test", user, restaurant);
        orderJoinGroupRepository.save(orderJoinGroup);

        boolean isDelete = orderJoinGroupDeleteService.delete(userId + 1L, orderJoinGroup.getId());

        assertFalse(isDelete);
        assertTrue(orderJoinGroupRepository.existsById(orderJoinGroup.getId()));
    }

    @Test
    void 오더메인_삭제_오더메인_고유번호_다름() {
        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);

        OrderJoinGroup orderJoinGroup = new OrderJoinGroup("test", "test", user, restaurant);
        orderJoinGroupRepository.save(orderJoinGroup);

        boolean isDelete = orderJoinGroupDeleteService.delete(userId, orderJoinGroup.getId() + 1L);

        assertFalse(isDelete);
        assertTrue(orderJoinGroupRepository.existsById(orderJoinGroup.getId()));
    }
}