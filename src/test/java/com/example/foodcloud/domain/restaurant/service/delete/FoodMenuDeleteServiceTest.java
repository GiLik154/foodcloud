package com.example.foodcloud.domain.restaurant.service.delete;

import com.example.foodcloud.domain.restaurant.domain.Restaurant;
import com.example.foodcloud.domain.restaurant.domain.RestaurantRepository;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FoodMenuDeleteServiceTest {
    private final RestaurantDeleter restaurantDeleter;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public FoodMenuDeleteServiceTest(RestaurantDeleter restaurantDeleter, RestaurantRepository restaurantRepository, UserRepository userRepository, PasswordEncoder bCryptPasswordEncoder) {
        this.restaurantDeleter = restaurantDeleter;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Test
    void 식당_삭제_정상작동() {
        User user = new User("test", bCryptPasswordEncoder.encode("test"), "test");
        userRepository.save(user);
        Long userId = user.getId();

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);
        Long restaurantId = restaurantRepository.findByUserId(userId).get(0).getId();

        restaurantDeleter.delete(userId, restaurantId, "test");

        assertFalse(restaurantRepository.existsById(restaurantId));
    }

    @Test
    void 식당_삭제_유저고유번호_다름() {
        User user = new User("test", bCryptPasswordEncoder.encode("test"), "test");
        userRepository.save(user);
        Long userId = user.getId();

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);
        Long restaurantId = restaurantRepository.findByUserId(userId).get(0).getId();

        assertThrows(UsernameNotFoundException.class, () -> restaurantDeleter.delete(userId + 1L, restaurantId, "test"));
        assertTrue(restaurantRepository.existsById(restaurantId));
    }

    @Test
    void 식당_삭제_식당고유번호_다름() {
//given
        User user = new User("test", bCryptPasswordEncoder.encode("test"), "test");
        userRepository.save(user);
        Long userId = user.getId();
        
        // 유저 저장

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);

        // 식당을 저장
        
        Long restaurantId = restaurantRepository.findByUserId(userId).get(0).getId();
//when
        
        restaurantDeleter.delete(userId, restaurantId + 1L, "test");
//then
        assertTrue(restaurantRepository.existsById(restaurantId));
    }

    @Test
    void 식당_삭제_비밀번호_다름() {
        User user = new User("test", bCryptPasswordEncoder.encode("test"), "test");
        userRepository.save(user);
        Long userId = user.getId();

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);
        Long restaurantId = restaurantRepository.findByUserId(userId).get(0).getId();

        BadCredentialsException e = assertThrows(BadCredentialsException.class, () ->
                restaurantDeleter.delete(userId, restaurantId, "test123")
        );

        assertTrue(restaurantRepository.existsById(restaurantId));
        assertEquals("Invalid password", e.getMessage());
    }
}