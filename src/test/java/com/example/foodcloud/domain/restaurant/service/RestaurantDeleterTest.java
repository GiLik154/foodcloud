package com.example.foodcloud.domain.restaurant.service;

import com.example.foodcloud.RestaurantFixture;
import com.example.foodcloud.UserFixture;
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
class RestaurantDeleterTest {
    private final RestaurantDeleter restaurantDeleter;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public RestaurantDeleterTest(RestaurantDeleter restaurantDeleter, RestaurantRepository restaurantRepository, UserRepository userRepository, PasswordEncoder bCryptPasswordEncoder) {
        this.restaurantDeleter = restaurantDeleter;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Test
    void 식당_삭제_정상작동() {
        User user = UserFixture.fixture().encoding(bCryptPasswordEncoder, "testPassword").build();
        userRepository.save(user);
        Long userId = user.getId();

        Restaurant restaurant = RestaurantFixture.fixture(user).build();
        restaurantRepository.save(restaurant);

        Long restaurantId = restaurantRepository.findById(restaurant.getId()).get().getId();

        restaurantDeleter.delete(userId, "testPassword", restaurantId);

        assertFalse(restaurantRepository.existsById(restaurantId));
    }

    @Test
    void 유저의_고유번호가_다르면_익셉션_발생() {
        User user = UserFixture.fixture().encoding(bCryptPasswordEncoder, "testPassword").build();
        userRepository.save(user);
        Long userId = user.getId();

        Restaurant restaurant = RestaurantFixture.fixture(user).build();
        restaurantRepository.save(restaurant);
        Long restaurantId = restaurantRepository.findByUserId(userId).get(0).getId();

        assertThrows(UsernameNotFoundException.class, () -> restaurantDeleter.delete(userId + 1L, "testPassword", restaurantId));
        assertTrue(restaurantRepository.existsById(restaurantId));
    }

    @Test
    void 식당의_고유번호가_다르면_삭제되지_않음() {
        User user = UserFixture.fixture().encoding(bCryptPasswordEncoder, "testPassword").build();
        userRepository.save(user);
        Long userId = user.getId();

        Restaurant restaurant = RestaurantFixture.fixture(user).build();
        restaurantRepository.save(restaurant);

        Long restaurantId = restaurantRepository.findById(restaurant.getId()).get().getId();

        restaurantDeleter.delete(userId, "testPassword", restaurantId + 1L);

        assertTrue(restaurantRepository.existsById(restaurantId));
    }

    @Test
    void 유저의_비밀번호가_다르면_익셉션_발생() {
        User user = UserFixture.fixture().encoding(bCryptPasswordEncoder, "testPassword").build();
        userRepository.save(user);
        Long userId = user.getId();

        Restaurant restaurant = RestaurantFixture.fixture(user).build();
        restaurantRepository.save(restaurant);

        Long restaurantId = restaurantRepository.findById(restaurant.getId()).get().getId();

        assertThrows(BadCredentialsException.class, () -> restaurantDeleter.delete(userId, "wrongPassword", restaurantId));

        assertTrue(restaurantRepository.existsById(restaurantId));
    }
}