package com.example.foodcloud.domain.foodmenu.service.delete;

import com.example.foodcloud.FoodMenuFixture;
import com.example.foodcloud.RestaurantFixture;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenuRepository;
import com.example.foodcloud.domain.foodmenu.service.FoodMenuDeleter;
import com.example.foodcloud.domain.restaurant.domain.Restaurant;
import com.example.foodcloud.domain.restaurant.domain.RestaurantRepository;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import com.example.foodcloud.exception.NotFoundFoodMenuException;
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
class FoodMenuDeleterImplTest {
    private final FoodMenuDeleter foodMenuDeleter;
    private final FoodMenuRepository foodMenuRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    FoodMenuDeleterImplTest(FoodMenuDeleter foodMenuDeleter, FoodMenuRepository foodMenuRepository, UserRepository userRepository, RestaurantRepository restaurantRepository, PasswordEncoder bCryptPasswordEncoder) {
        this.foodMenuDeleter = foodMenuDeleter;
        this.foodMenuRepository = foodMenuRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Test
    void 음식메뉴_삭제_정상작동() {
        User user = new User("test", bCryptPasswordEncoder.encode("test"), "test");
        userRepository.save(user);
        Long userId = user.getId();

        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = foodMenuRepository.save(FoodMenuFixture.fixture(restaurant).build());
        foodMenuRepository.save(foodMenu);
        Long foodMenuId = foodMenu.getId();

        foodMenuDeleter.delete(userId, foodMenuId, "test");

        assertFalse(foodMenuRepository.existsById(foodMenuId));
    }

    @Test
    void 음식메뉴_삭제_유저고유번호_다름() {
        User user = new User("test", bCryptPasswordEncoder.encode("test"), "test");
        userRepository.save(user);
        Long userId = user.getId();

        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = foodMenuRepository.save(FoodMenuFixture.fixture(restaurant).build());
        foodMenuRepository.save(foodMenu);
        Long foodMenuId = foodMenu.getId();


        assertThrows(UsernameNotFoundException.class, () -> foodMenuDeleter.delete(userId + 1L, foodMenuId, "test123"));

        assertTrue(foodMenuRepository.existsById(foodMenuId));
    }

    @Test
    void 음식메뉴_삭제_계좌고유번호_다름() {
        User user = new User("test", bCryptPasswordEncoder.encode("test"), "test");
        userRepository.save(user);
        Long userId = user.getId();

        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = foodMenuRepository.save(FoodMenuFixture.fixture(restaurant).build());
        foodMenuRepository.save(foodMenu);
        Long foodMenuId = foodMenu.getId();

        assertThrows(NotFoundFoodMenuException.class, () -> foodMenuDeleter.delete(userId, foodMenuId + 1L, "test"));

        assertTrue(foodMenuRepository.existsById(foodMenuId));
    }

    @Test
    void 음식메뉴_삭제_비밀번호_다름() {
        User user = new User("test", bCryptPasswordEncoder.encode("test"), "test");
        userRepository.save(user);
        Long userId = user.getId();

        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = foodMenuRepository.save(FoodMenuFixture.fixture(restaurant).build());
        foodMenuRepository.save(foodMenu);
        Long foodMenuId = foodMenu.getId();

        assertThrows(BadCredentialsException.class, () -> foodMenuDeleter.delete(userId, foodMenuId, "test123"));

        assertTrue(foodMenuRepository.existsById(foodMenuId));
    }
}