package com.example.foodcloud.domain.restaurant.service.update;

import com.example.foodcloud.domain.restaurant.domain.Restaurant;
import com.example.foodcloud.domain.restaurant.domain.RestaurantRepository;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import com.example.foodcloud.domain.restaurant.service.update.dto.RestaurantUpdateServiceDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RestaurantUpdateServiceImplTest {
    private final RestaurantUpdateService restaurantUpdateService;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    @Autowired
    public RestaurantUpdateServiceImplTest(RestaurantUpdateService restaurantUpdateService, RestaurantRepository restaurantRepository, UserRepository userRepository) {
        this.restaurantUpdateService = restaurantUpdateService;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    @Test
    void 식당_정보_업데이트_정상작동() {
        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);
        Long restaurantId = restaurant.getId();

        RestaurantUpdateServiceDto restaurantUpdateServiceDto = new RestaurantUpdateServiceDto("test123", "test123", "test123");
        boolean isUpdate = restaurantUpdateService.update(userId, restaurantId, restaurantUpdateServiceDto);

        assertTrue(isUpdate);
        assertEquals("test123", restaurant.getName());
        assertEquals("test123", restaurant.getBusinessHours());
    }

    @Test
    void 식당_정보_업데이트_유저고유번호_다름() {
        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);
        Long restaurantId = restaurant.getId();

        RestaurantUpdateServiceDto restaurantUpdateServiceDto = new RestaurantUpdateServiceDto("test123", "test123", "test123");
        boolean isUpdate = restaurantUpdateService.update(userId + 1L, restaurantId, restaurantUpdateServiceDto);

        assertFalse(isUpdate);
        assertEquals("test", restaurant.getName(), "test");
        assertEquals("test", restaurant.getLocation(), "test");
        assertEquals("test", restaurant.getBusinessHours(), "test");
    }

    @Test
    void 식당_정보_업데이트_식당고유번호_다름() {
        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);
        Long restaurantId = restaurant.getId();

        RestaurantUpdateServiceDto restaurantUpdateServiceDto = new RestaurantUpdateServiceDto("test123", "test123","test123");
        boolean isUpdate = restaurantUpdateService.update(userId, restaurantId + 1L, restaurantUpdateServiceDto);

        assertFalse(isUpdate);
        assertEquals("test", restaurant.getName());
        assertEquals("test", restaurant.getLocation());
        assertEquals("test", restaurant.getBusinessHours());
    }
}