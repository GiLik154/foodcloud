package com.example.foodcloud.domain.restaurant.service.update;

import com.example.foodcloud.UserFixtures;
import com.example.foodcloud.domain.restaurant.domain.Restaurant;
import com.example.foodcloud.domain.restaurant.domain.RestaurantRepository;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import com.example.foodcloud.domain.restaurant.service.update.dto.RestaurantUpdateServiceDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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

    private User user;
    private Restaurant restaurant;

    @BeforeEach
    public void init(){
        user = UserFixtures.anUserFixtures().build();

        restaurant = new Restaurant("testName", "testLocation", "testHours", user);
    }

    @Test
    void 식당_정보_업데이트_정상작동() {
        userRepository.save(user);
        restaurantRepository.save(restaurant);

        Long userId = user.getId();
        Long restaurantId = restaurant.getId();

        RestaurantUpdateServiceDto restaurantUpdateServiceDto = new RestaurantUpdateServiceDto("newName", "newLocation", "newHours");
        restaurantUpdater.update(userId, restaurantId, restaurantUpdateServiceDto);

        assertEquals("newName", restaurant.getName());
        assertEquals("newLocation", restaurant.getLocation());
        assertEquals("newHours", restaurant.getBusinessHours());
    }

    @Test
    void 유저의_고유번호가_다르먼_업데이트_되지_않음() {
        userRepository.save(user);
        restaurantRepository.save(restaurant);

        Long userId = user.getId();
        Long restaurantId = restaurant.getId();

        RestaurantUpdateServiceDto restaurantUpdateServiceDto = new RestaurantUpdateServiceDto("newName", "newLocation", "newHours");
        restaurantUpdater.update(userId + 1L, restaurantId, restaurantUpdateServiceDto);

        assertEquals("testName", restaurant.getName());
        assertEquals("testLocation", restaurant.getLocation());
        assertEquals("testHours", restaurant.getBusinessHours());
    }

    @Test
    void 식당의_고유번호가_다르면_업데이트_되지_않음() {
        userRepository.save(user);
        restaurantRepository.save(restaurant);

        Long userId = user.getId();
        Long restaurantId = restaurant.getId();

        RestaurantUpdateServiceDto restaurantUpdateServiceDto = new RestaurantUpdateServiceDto("newName", "newLocation", "newHours");
        restaurantUpdater.update(userId, restaurantId + 1L, restaurantUpdateServiceDto);

        assertEquals("testName", restaurant.getName());
        assertEquals("testLocation", restaurant.getLocation());
        assertEquals("testHours", restaurant.getBusinessHours());
    }

    //todo 여기서부터 다시 시작
}