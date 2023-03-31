package com.example.foodcloud.domain.foodmenu.service.update;

import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenuRepository;
import com.example.foodcloud.domain.foodmenu.service.update.dto.FoodMenuUpdateServiceDto;
import com.example.foodcloud.domain.restaurant.domain.Restaurant;
import com.example.foodcloud.domain.restaurant.domain.RestaurantRepository;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import com.example.foodcloud.enums.OrderResult;
import com.example.foodcloud.enums.foodmenu.FoodTypes;
import com.example.foodcloud.enums.foodmenu.MeatTypes;
import com.example.foodcloud.enums.foodmenu.Temperature;
import com.example.foodcloud.enums.foodmenu.Vegetables;
import com.example.foodcloud.exception.NotFoundRestaurantException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FoodMenuUpdateServiceImplTest {
    private final FoodMenuUpdateService foodMenuUpdateService;
    private final FoodMenuRepository foodMenuRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    @Autowired
    public FoodMenuUpdateServiceImplTest(FoodMenuUpdateService foodMenuUpdateService, FoodMenuRepository foodMenuRepository, RestaurantRepository restaurantRepository, UserRepository userRepository) {
        this.foodMenuUpdateService = foodMenuUpdateService;
        this.foodMenuRepository = foodMenuRepository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    @Test
    void 음식메뉴_수정_정상작동() throws IOException {
        File file = new File("test.jpg");
        file.createNewFile();

        User user = new User("test", "test", "test");
        userRepository.save(user);

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);
        Long restaurantId = restaurant.getId();

        FoodMenu foodMenu = new FoodMenu("test", 5000, Temperature.COLD, FoodTypes.ADE, MeatTypes.CHICKEN, Vegetables.FEW, restaurant);
        foodMenuRepository.save(foodMenu);
        Long foodMenuId = foodMenu.getId();

        FoodMenuUpdateServiceDto foodMenuUpdateServiceDto = new FoodMenuUpdateServiceDto("test123", 3000, Temperature.COLD, FoodTypes.ADE, MeatTypes.CHICKEN, Vegetables.FEW);
        boolean isUpdate = foodMenuUpdateService.update(foodMenuId, restaurantId, foodMenuUpdateServiceDto, file);

        assertTrue(isUpdate);
        assertEquals("test123", foodMenu.getName());
        assertEquals(3000, foodMenu.getPrice());
        assertEquals(FoodTypes.ADE, foodMenu.getFoodTypes());
        assertEquals(Temperature.COLD, foodMenu.getTemperature());
        assertEquals(MeatTypes.CHICKEN, foodMenu.getMeatType());
        assertEquals(Vegetables.FEW, foodMenu.getVegetables());
        assertNotNull(foodMenu.getImagePath());
    }

    @Test
    void 음식메뉴_수정_이미지없음() {
        File file = null;

        User user = new User("test", "test", "test");
        userRepository.save(user);

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);
        Long restaurantId = restaurant.getId();

        FoodMenu foodMenu = new FoodMenu("test", 5000, Temperature.COLD, FoodTypes.ADE, MeatTypes.CHICKEN, Vegetables.FEW, restaurant);
        foodMenuRepository.save(foodMenu);
        Long foodMenuId = foodMenu.getId();

        FoodMenuUpdateServiceDto foodMenuUpdateServiceDto = new FoodMenuUpdateServiceDto("test123", 3000, Temperature.COLD, FoodTypes.ADE, MeatTypes.CHICKEN, Vegetables.FEW);
        boolean isUpdate = foodMenuUpdateService.update(foodMenuId, restaurantId, foodMenuUpdateServiceDto, file);

        assertTrue(isUpdate);
        assertEquals("test123", foodMenu.getName());
        assertEquals(3000, foodMenu.getPrice());
        assertEquals(FoodTypes.ADE, foodMenu.getFoodTypes());
        assertEquals(Temperature.COLD, foodMenu.getTemperature());
        assertEquals(MeatTypes.CHICKEN, foodMenu.getMeatType());
        assertEquals(Vegetables.FEW, foodMenu.getVegetables());
        assertNull(foodMenu.getImagePath());
    }

    @Test
    void 음식메뉴_수정_음식고유번호_다름() {
        File file = mock(File.class);

        User user = new User("test", "test", "test");
        userRepository.save(user);

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);
        Long restaurantId = restaurant.getId();

        FoodMenu foodMenu = new FoodMenu("test", 5000, Temperature.COLD, FoodTypes.ADE, MeatTypes.CHICKEN, Vegetables.FEW, restaurant);
        foodMenuRepository.save(foodMenu);
        Long foodMenuId = foodMenu.getId();

        FoodMenuUpdateServiceDto foodMenuUpdateServiceDto = new FoodMenuUpdateServiceDto("test123", 3000, Temperature.COLD, FoodTypes.ADE, MeatTypes.CHICKEN, Vegetables.FEW);
        boolean isUpdate = foodMenuUpdateService.update(foodMenuId + 1L, restaurantId, foodMenuUpdateServiceDto, file);

        assertFalse(isUpdate);
    }

    @Test
    void 음식메뉴_수정_식당고유번호_다름() {
        File file = mock(File.class);

        User user = new User("test", "test", "test");
        userRepository.save(user);

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);
        Long restaurantId = restaurant.getId();

        FoodMenu foodMenu = new FoodMenu("test", 5000, Temperature.COLD, FoodTypes.ADE, MeatTypes.CHICKEN, Vegetables.FEW, restaurant);
        foodMenuRepository.save(foodMenu);
        Long foodMenuId = foodMenu.getId();

        FoodMenuUpdateServiceDto foodMenuUpdateServiceDto = new FoodMenuUpdateServiceDto("test123", 3000, Temperature.COLD, FoodTypes.ADE, MeatTypes.CHICKEN, Vegetables.FEW);

        NotFoundRestaurantException e = assertThrows(NotFoundRestaurantException.class, () ->
                foodMenuUpdateService.update(foodMenuId, restaurantId + 1L, foodMenuUpdateServiceDto, file)
        );

        assertEquals("Not found Restaurant", e.getMessage());
    }
}