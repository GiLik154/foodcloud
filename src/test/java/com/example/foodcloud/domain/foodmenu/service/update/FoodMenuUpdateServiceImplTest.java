package com.example.foodcloud.domain.foodmenu.service.update;

import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenuRepository;
import com.example.foodcloud.domain.foodmenu.service.dto.FoodMenuDto;
import com.example.foodcloud.domain.foodmenu.service.update.FoodMenuUpdateService;
import com.example.foodcloud.domain.restaurant.domain.Restaurant;
import com.example.foodcloud.domain.restaurant.domain.RestaurantRepository;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import com.example.foodcloud.exception.NotFoundRestaurantException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

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
    void 음식메뉴_수정_정상작동() {
        byte[] imageBytes = "test-image".getBytes();
        String imageName = "test-image.jpg";
        MockMultipartFile file = new MockMultipartFile("file", imageName, "image/jpeg", imageBytes);

        User user = new User("test", "test", "test");
        userRepository.save(user);

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);
        Long restaurantId = restaurant.getId();

        FoodMenu foodMenu = new FoodMenu("test", 5000, "test", "test", "test", "test", restaurant);
        foodMenuRepository.save(foodMenu);
        Long foodMenuId = foodMenu.getId();

        FoodMenuDto foodMenuDto = new FoodMenuDto("test123", 3000, "test123", "test123", "test123", "test123");
        boolean isUpdate = foodMenuUpdateService.update(foodMenuId, restaurantId, foodMenuDto, file);

        assertTrue(isUpdate);
        assertEquals("test123", foodMenu.getFoodMenuName());
        assertEquals(3000, foodMenu.getPrice());
        assertEquals("test123", foodMenu.getFoodType());
        assertEquals("test123", foodMenu.getTemperature());
        assertEquals("test123", foodMenu.getMeatType());
        assertEquals("test123", foodMenu.getVegetables());
        assertNotNull(foodMenu.getImagePath());
    }

    @Test
    void 음식메뉴_수정_이미지없음() {
        MockMultipartFile file = null;

        User user = new User("test", "test", "test");
        userRepository.save(user);

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);
        Long restaurantId = restaurant.getId();

        FoodMenu foodMenu = new FoodMenu("test", 5000, "test", "test", "test", "test", restaurant);
        foodMenuRepository.save(foodMenu);
        Long foodMenuId = foodMenu.getId();

        FoodMenuDto foodMenuDto = new FoodMenuDto("test123", 3000, "test123", "test123", "test123", "test123");
        boolean isUpdate = foodMenuUpdateService.update(foodMenuId, restaurantId, foodMenuDto, file);

        assertTrue(isUpdate);
        assertEquals("test123", foodMenu.getFoodMenuName());
        assertEquals(3000, foodMenu.getPrice());
        assertEquals("test123", foodMenu.getFoodType());
        assertEquals("test123", foodMenu.getTemperature());
        assertEquals("test123", foodMenu.getMeatType());
        assertEquals("test123", foodMenu.getVegetables());
        assertNull(foodMenu.getImagePath());
    }

    @Test
    void 음식메뉴_수정_음식고유번호_다름() {
        byte[] imageBytes = "test-image".getBytes();
        String imageName = "test-image.jpg";
        MockMultipartFile file = new MockMultipartFile("file", imageName, "image/jpeg", imageBytes);

        User user = new User("test", "test", "test");
        userRepository.save(user);

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);
        Long restaurantId = restaurant.getId();

        FoodMenu foodMenu = new FoodMenu("test", 5000, "test", "test", "test", "test", restaurant);
        foodMenuRepository.save(foodMenu);
        Long foodMenuId = foodMenu.getId();

        FoodMenuDto foodMenuDto = new FoodMenuDto("test123", 3000, "test123", "test123", "test123", "test123");
        boolean isUpdate = foodMenuUpdateService.update(foodMenuId + 1L, restaurantId, foodMenuDto, file);

        assertFalse(isUpdate);
    }

    @Test
    void 음식메뉴_수정_식당고유번호_다름() {
        byte[] imageBytes = "test-image".getBytes();
        String imageName = "test-image.jpg";
        MockMultipartFile file = new MockMultipartFile("file", imageName, "image/jpeg", imageBytes);

        User user = new User("test", "test", "test");
        userRepository.save(user);

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);
        Long restaurantId = restaurant.getId();

        FoodMenu foodMenu = new FoodMenu("test", 5000, "test", "test", "test", "test", restaurant);
        foodMenuRepository.save(foodMenu);
        Long foodMenuId = foodMenu.getId();

        FoodMenuDto foodMenuDto = new FoodMenuDto("test123", 3000, "test123", "test123", "test123", "test123");

        NotFoundRestaurantException e = assertThrows(NotFoundRestaurantException.class, () ->
                foodMenuUpdateService.update(foodMenuId, restaurantId + 1L, foodMenuDto, file)
        );

        assertEquals("Not found restaurant", e.getMessage());
    }
}