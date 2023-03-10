package com.example.foodcloud.domain.foodmenu.service.delete;

import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenuRepository;
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
class FoodMenuDeleteServiceImplTest {
    private final FoodMenuDeleteService foodMenuDeleteService;
    private final FoodMenuRepository foodMenuRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    FoodMenuDeleteServiceImplTest(FoodMenuDeleteService foodMenuDeleteService, FoodMenuRepository foodMenuRepository, UserRepository userRepository, RestaurantRepository restaurantRepository, PasswordEncoder bCryptPasswordEncoder) {
        this.foodMenuDeleteService = foodMenuDeleteService;
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

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = new FoodMenu("test", 5000, "test", "test", "test", "test", restaurant);
        foodMenuRepository.save(foodMenu);
        Long foodMenuId = foodMenu.getId();

        boolean isDelete = foodMenuDeleteService.delete(userId, foodMenuId, "test");

        assertTrue(isDelete);
        assertFalse(foodMenuRepository.existsById(foodMenuId));
    }

    @Test
    void 음식메뉴_삭제_유저고유번호_다름() {
        User user = new User("test", bCryptPasswordEncoder.encode("test"), "test");
        userRepository.save(user);
        Long userId = user.getId();

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = new FoodMenu("test", 5000, "test", "test", "test", "test", restaurant);
        foodMenuRepository.save(foodMenu);
        Long foodMenuId = foodMenu.getId();


        UsernameNotFoundException e = assertThrows(UsernameNotFoundException.class, () ->
                foodMenuDeleteService.delete(userId + 1L, foodMenuId, "test123")
        );

        assertEquals("Invalid user", e.getMessage());
        assertTrue(foodMenuRepository.existsById(foodMenuId));
    }

    @Test
    void 음식메뉴_삭제_계좌고유번호_다름() {
        User user = new User("test", bCryptPasswordEncoder.encode("test"), "test");
        userRepository.save(user);
        Long userId = user.getId();

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = new FoodMenu("test", 5000, "test", "test", "test", "test", restaurant);
        foodMenuRepository.save(foodMenu);
        Long foodMenuId = foodMenu.getId();

        boolean isDelete = foodMenuDeleteService.delete(userId, foodMenuId + 1L, "test");

        assertFalse(isDelete);
        assertTrue(foodMenuRepository.existsById(foodMenuId));
    }

    @Test
    void 음식메뉴_삭제_비밀번호_다름() {
        User user = new User("test", bCryptPasswordEncoder.encode("test"), "test");
        userRepository.save(user);
        Long userId = user.getId();

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = new FoodMenu("test", 5000, "test", "test", "test", "test", restaurant);
        foodMenuRepository.save(foodMenu);
        Long foodMenuId = foodMenu.getId();

        BadCredentialsException e = assertThrows(BadCredentialsException.class, () ->
                foodMenuDeleteService.delete(userId, foodMenuId, "test123")
        );

        assertEquals("Invalid password", e.getMessage());
        assertTrue(foodMenuRepository.existsById(foodMenuId));
    }
}