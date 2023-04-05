package com.example.foodcloud.domain.order.join.service.add;

import com.example.foodcloud.domain.order.join.domain.OrderJoinGroup;
import com.example.foodcloud.domain.order.join.domain.OrderJoinGroupRepository;
import com.example.foodcloud.domain.order.join.service.add.dto.OrderJoinGroupAddServiceDto;
import com.example.foodcloud.domain.restaurant.domain.Restaurant;
import com.example.foodcloud.domain.restaurant.domain.RestaurantRepository;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import com.example.foodcloud.enums.OrderResult;
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
class OrderJoinGroupAddServiceImplTest {
    private final OrderJoinGroupAddService orderJoinGroupAddService;
    private final OrderJoinGroupRepository orderJoinGroupRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public OrderJoinGroupAddServiceImplTest(OrderJoinGroupAddService OrderJoinGroupAddService,
                                            OrderJoinGroupRepository OrderJoinGroupRepository,
                                            UserRepository userRepository,
                                            RestaurantRepository restaurantRepository) {

        this.orderJoinGroupAddService = OrderJoinGroupAddService;
        this.orderJoinGroupRepository = OrderJoinGroupRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Test
    void 오더_메뉴_추가_정삭상작동() {
        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);
        Long restaurantId = restaurant.getId();

        OrderJoinGroupAddServiceDto OrderJoinGroupAddServiceDto = new OrderJoinGroupAddServiceDto("test", restaurantId);
        orderJoinGroupAddService.add(userId, OrderJoinGroupAddServiceDto);
        OrderJoinGroup orderJoinGroup = orderJoinGroupRepository.findByUserId(userId).get(0);

        assertEquals("test", orderJoinGroup.getLocation());
        assertEquals(OrderResult.PAYMENT_WAITING, orderJoinGroup.getResult());
        assertEquals(user, orderJoinGroup.getUser());
        assertEquals(restaurant, orderJoinGroup.getRestaurant());
        assertNotNull(orderJoinGroup.getTime());
    }

    @Test
    void 오더_메뉴_추가_유저고유번호_다름() {
        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);
        Long restaurantId = restaurantRepository.findByUserId(userId).get(0).getId();

        OrderJoinGroupAddServiceDto OrderJoinGroupAddServiceDto = new OrderJoinGroupAddServiceDto("test", restaurantId);

        UsernameNotFoundException e = assertThrows(UsernameNotFoundException.class, () ->
                orderJoinGroupAddService.add(userId + 1L, OrderJoinGroupAddServiceDto)
        );
    }

    @Test
    void 오더_메뉴_추가_식당고유번호_다름() {
        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);
        Long restaurantId = restaurantRepository.findByUserId(userId).get(0).getId();

        OrderJoinGroupAddServiceDto OrderJoinGroupAddServiceDto = new OrderJoinGroupAddServiceDto("test", restaurantId + 1L);

        NotFoundRestaurantException e = assertThrows(NotFoundRestaurantException.class, () ->
                orderJoinGroupAddService.add(userId, OrderJoinGroupAddServiceDto)
        );
        assertEquals("Not found Restaurant", e.getMessage());
    }
}