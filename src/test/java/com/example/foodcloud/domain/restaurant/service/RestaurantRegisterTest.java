package com.example.foodcloud.domain.restaurant.service;

import com.example.foodcloud.UserFixture;
import com.example.foodcloud.domain.restaurant.domain.Restaurant;
import com.example.foodcloud.domain.restaurant.domain.RestaurantRepository;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import com.example.foodcloud.domain.restaurant.service.commend.RestaurantRegisterCommend;
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
class RestaurantRegisterTest {
    private final RestaurantRegister restaurantRegister;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    @Autowired
    public RestaurantRegisterTest(RestaurantRegister restaurantRegister, RestaurantRepository restaurantRepository, UserRepository userRepository) {
        this.restaurantRegister = restaurantRegister;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    @Test
    void 식당_추가_정상작동() {
        User user = userRepository.save(UserFixture.fixture().build());
        Long userId = user.getId();

        RestaurantRegisterCommend restaurantRegisterCommend = new RestaurantRegisterCommend("testName", "testLocation", "testHours");
        restaurantRegister.add(userId, restaurantRegisterCommend);

        Restaurant restaurant = restaurantRepository.findByUserId(userId).get(0);

        assertEquals("testName", restaurant.getName());
        assertEquals("testLocation", restaurant.getLocation());
        assertEquals("testHours", restaurant.getBusinessHours());
        assertEquals(user, restaurant.getUser());
    }

    @Test
    void 유저의_고유번호가_다르면_익셉션_발생() {
        User user = userRepository.save(UserFixture.fixture().build());
        Long userId = user.getId();

        RestaurantRegisterCommend restaurantRegisterCommend = new RestaurantRegisterCommend("testName", "testLocation",  "testHours");

        assertThrows(UsernameNotFoundException.class, () -> restaurantRegister.add(userId + 1L, restaurantRegisterCommend));
    }
}