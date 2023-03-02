package com.example.foodcloud.domain.restaurant.service.add;

import com.example.foodcloud.domain.restaurant.domain.Restaurant;
import com.example.foodcloud.domain.restaurant.domain.RestaurantRepository;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import com.example.foodcloud.domain.restaurant.service.add.dto.RestaurantAddDto;
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
class RestaurantAddServiceImplTest {
    private final RestaurantAddService restaurantAddService;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    @Autowired
    public RestaurantAddServiceImplTest(RestaurantAddService restaurantAddService, RestaurantRepository restaurantRepository, UserRepository userRepository) {
        this.restaurantAddService = restaurantAddService;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    @Test
    void 식당_추가_정상작동() {
        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();

        RestaurantAddDto restaurantAddDto = new RestaurantAddDto("test", "test", "test");
        restaurantAddService.add(userId, restaurantAddDto);

        Restaurant restaurant = restaurantRepository.findByUserId(userId).get(0);

        assertEquals("test", restaurant.getName());
        assertEquals("test", restaurant.getLocation());
        assertEquals("test", restaurant.getBusinessHours());
    }

    @Test
    void 식당_추가_아이디_다름() {
        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();

        RestaurantAddDto restaurantAddDto = new RestaurantAddDto("test", "test",  "test");

        UsernameNotFoundException e = assertThrows(UsernameNotFoundException.class, () ->
                restaurantAddService.add(userId + 1L, restaurantAddDto)
        );

        assertEquals("Invalid user", e.getMessage());
    }
}