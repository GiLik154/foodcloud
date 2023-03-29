package com.example.foodcloud.domain.foodmenu.service.add;

import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenuRepository;
import com.example.foodcloud.domain.foodmenu.service.add.dto.FoodMenuAddServiceDto;
import com.example.foodcloud.domain.restaurant.domain.Restaurant;
import com.example.foodcloud.domain.restaurant.domain.RestaurantRepository;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import com.example.foodcloud.enums.foodmenu.FoodTypes;
import com.example.foodcloud.enums.foodmenu.MeatTypes;
import com.example.foodcloud.enums.foodmenu.Temperature;
import com.example.foodcloud.enums.foodmenu.Vegetables;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FoodMenuAddServiceImplTest {
    private final FoodMenuAddService foodMenuAddService;
    private final FoodMenuRepository foodMenuRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    @Autowired
    public FoodMenuAddServiceImplTest(FoodMenuAddService foodMenuAddService, FoodMenuRepository foodMenuRepository, RestaurantRepository restaurantRepository, UserRepository userRepository) {
        this.foodMenuAddService = foodMenuAddService;
        this.foodMenuRepository = foodMenuRepository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    @Test
    void 음식메뉴_추가_정상작동() {
        File file = mock(File.class);

        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);
        Long restaurantId = restaurantRepository.findByUserId(userId).get(0).getId();

        FoodMenuAddServiceDto foodMenuAddServiceDto = new FoodMenuAddServiceDto("test", 5000, Temperature.COLD, FoodTypes.ADE, MeatTypes.CHICKEN, Vegetables.FEW);
        boolean isAdd = foodMenuAddService.add(userId, restaurantId, foodMenuAddServiceDto, file);
        FoodMenu foodMenu = foodMenuRepository.findByRestaurantId(restaurantId).get(0);

        assertTrue(isAdd);
        assertEquals("test", foodMenu.getName());
        assertEquals(5000, foodMenu.getPrice());
        assertEquals("test", foodMenu.getFoodTypes());
        assertEquals("test", foodMenu.getTemperature());
        assertEquals("test", foodMenu.getMeatType());
        assertEquals("test", foodMenu.getVegetables());
        assertNotNull(foodMenu.getImagePath());
    }

    @Test
    void 음식메뉴_추가_이미지없음() {
        File file = null;

        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);
        Long restaurantId = restaurantRepository.findByUserId(userId).get(0).getId();

        FoodMenuAddServiceDto foodMenuAddServiceDto = new FoodMenuAddServiceDto("test", 5000, Temperature.COLD, FoodTypes.ADE, MeatTypes.CHICKEN, Vegetables.FEW);
        boolean isAdd = foodMenuAddService.add(userId, restaurantId, foodMenuAddServiceDto, file);
        FoodMenu foodMenu = foodMenuRepository.findByRestaurantId(restaurantId).get(0);

        assertTrue(isAdd);
        assertEquals("test", foodMenu.getName());
        assertEquals(5000, foodMenu.getPrice());
        assertEquals("test", foodMenu.getFoodTypes());
        assertEquals("test", foodMenu.getTemperature());
        assertEquals("test", foodMenu.getMeatType());
        assertEquals("test", foodMenu.getVegetables());
        assertNull(foodMenu.getImagePath());
    }

    @Test
    void 음식메뉴_추가_유저고유번호_다름() {
        File file = mock(File.class);

        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);
        Long restaurantId = restaurantRepository.findByUserId(userId).get(0).getId();

        FoodMenuAddServiceDto foodMenuAddServiceDto = new FoodMenuAddServiceDto("test", 5000, Temperature.COLD, FoodTypes.ADE, MeatTypes.CHICKEN, Vegetables.FEW);
        boolean isAdd = foodMenuAddService.add(userId + 1L, restaurantId, foodMenuAddServiceDto, file);

        assertFalse(isAdd);
    }

    @Test
    void 음식메뉴_추가_식당고유번호_다름() {
        File file = mock(File.class);

        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);
        Long restaurantId = restaurantRepository.findByUserId(userId).get(0).getId();

        FoodMenuAddServiceDto foodMenuAddServiceDto = new FoodMenuAddServiceDto("test", 5000, Temperature.COLD, FoodTypes.ADE, MeatTypes.CHICKEN, Vegetables.FEW);
        boolean isAdd = foodMenuAddService.add(userId, restaurantId + 1L, foodMenuAddServiceDto, file);

        assertFalse(isAdd);
    }
}