package com.example.foodcloud.domain.restaurant.service;

import com.example.foodcloud.RestaurantFixture;
import com.example.foodcloud.UserFixture;
import com.example.foodcloud.domain.restaurant.domain.Restaurant;
import com.example.foodcloud.domain.restaurant.domain.RestaurantRepository;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import com.example.foodcloud.domain.restaurant.service.commend.RestaurantUpdaterCommend;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RestaurantUpdaterTest {
    private final RestaurantUpdater restaurantUpdater;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    @Autowired
    public RestaurantUpdaterTest(RestaurantUpdater restaurantUpdater, RestaurantRepository restaurantRepository, UserRepository userRepository) {
        this.restaurantUpdater = restaurantUpdater;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    @Test
    void 식당_정보_업데이트_정상작동() {
        User user = UserFixture.fixture().build();
        userRepository.save(user);
        Long userId = user.getId();

        Restaurant restaurant = RestaurantFixture.fixture(user).build();
        restaurantRepository.save(restaurant);
        Long restaurantId = restaurant.getId();

        RestaurantUpdaterCommend restaurantUpdaterCommend = new RestaurantUpdaterCommend("newName", "newLocation", "newHours");
        restaurantUpdater.update(userId, restaurantId, restaurantUpdaterCommend);

        assertEquals("newName", restaurant.getName());
        assertEquals("newLocation", restaurant.getLocation());
        assertEquals("newHours", restaurant.getBusinessHours());
    }

    @Test
    void 유저의_고유번호가_다르먼_업데이트_되지_않음() {
        User user = UserFixture.fixture().build();
        userRepository.save(user);
        Long userId = user.getId();

        Restaurant restaurant = RestaurantFixture.fixture(user).build();
        restaurantRepository.save(restaurant);
        Long restaurantId = restaurant.getId();

        RestaurantUpdaterCommend restaurantUpdaterCommend = new RestaurantUpdaterCommend("newName", "newLocation", "newHours");
        restaurantUpdater.update(userId + 1L, restaurantId, restaurantUpdaterCommend);

        assertEquals("testRestaurantName", restaurant.getName());
        assertEquals("testLocation", restaurant.getLocation());
        assertEquals("testHours", restaurant.getBusinessHours());
    }

    @Test
    void 식당의_고유번호가_다르면_업데이트_되지_않음() {
        User user = UserFixture.fixture().build();
        userRepository.save(user);
        Long userId = user.getId();

        Restaurant restaurant = RestaurantFixture.fixture(user).build();
        restaurantRepository.save(restaurant);
        Long restaurantId = restaurant.getId();

        RestaurantUpdaterCommend restaurantUpdaterCommend = new RestaurantUpdaterCommend("newName", "newLocation", "newHours");
        restaurantUpdater.update(userId, restaurantId + 1L, restaurantUpdaterCommend);

        assertEquals("testRestaurantName", restaurant.getName());
        assertEquals("testLocation", restaurant.getLocation());
        assertEquals("testHours", restaurant.getBusinessHours());
    }
}