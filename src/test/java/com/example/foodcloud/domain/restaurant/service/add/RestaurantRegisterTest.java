package com.example.foodcloud.domain.restaurant.service.add;

import com.example.foodcloud.domain.restaurant.domain.Restaurant;
import com.example.foodcloud.domain.restaurant.domain.RestaurantRepository;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import com.example.foodcloud.domain.restaurant.service.add.dto.RestaurantAddServiceDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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
        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();

        RestaurantAddServiceDto restaurantAddServiceDto = new RestaurantAddServiceDto("test", "test", "test");
        restaurantRegister.add(userId, restaurantAddServiceDto);

        Restaurant restaurant = restaurantRepository.findByUserId(userId).get(0);

        assertEquals("test", restaurant.getName());
        assertEquals("test", restaurant.getLocation());
        assertEquals("test", restaurant.getBusinessHours());
        assertEquals(user, restaurant.getUser());
    }

    @Test
    void 식당_추가_아이디_다름() {
        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();

        RestaurantAddServiceDto restaurantAddServiceDto = new RestaurantAddServiceDto("test", "test",  "test");

        UsernameNotFoundException e = assertThrows(UsernameNotFoundException.class, () ->
                restaurantRegister.add(userId + 1L, restaurantAddServiceDto)
        );

        assertEquals("User not found", e.getMessage());
    }
}