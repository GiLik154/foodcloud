package com.example.foodcloud.domain.foodmenu.service.update;

import com.example.foodcloud.FoodMenuFixture;
import com.example.foodcloud.RestaurantFixture;
import com.example.foodcloud.UserFixture;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenuRepository;
import com.example.foodcloud.domain.foodmenu.service.update.dto.FoodMenuUpdateServiceDto;
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

        User user = userRepository.save(UserFixture.fixture().build());
        userRepository.save(user);

        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());
        restaurantRepository.save(restaurant);
        Long restaurantId = restaurant.getId();

        FoodMenu foodMenu = foodMenuRepository.save(FoodMenuFixture.fixture(restaurant).build());
        foodMenuRepository.save(foodMenu);
        Long foodMenuId = foodMenu.getId();

        FoodMenuUpdateServiceDto foodMenuUpdateServiceDto = new FoodMenuUpdateServiceDto("test123", 3000, Temperature.COLD, FoodTypes.ADE, MeatTypes.CHICKEN, Vegetables.FEW);
        foodMenuUpdateService.update(foodMenuId, foodMenuUpdateServiceDto, file);

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

        User user = userRepository.save(UserFixture.fixture().build());
        userRepository.save(user);

        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = foodMenuRepository.save(FoodMenuFixture.fixture(restaurant).build());
        foodMenuRepository.save(foodMenu);
        Long foodMenuId = foodMenu.getId();

        FoodMenuUpdateServiceDto foodMenuUpdateServiceDto = new FoodMenuUpdateServiceDto("updateTest", 3000, Temperature.HOT, FoodTypes.AMERICANO, MeatTypes.BEEF, Vegetables.MANY);
        foodMenuUpdateService.update(foodMenuId, foodMenuUpdateServiceDto, file);

        FoodMenu updateFoodMenu = foodMenuRepository.findById(foodMenuId).get();

        assertEquals("updateTest", updateFoodMenu.getName());
        assertEquals(3000, updateFoodMenu.getPrice());
        assertEquals(Temperature.HOT, updateFoodMenu.getTemperature());
        assertEquals(FoodTypes.AMERICANO, updateFoodMenu.getFoodTypes());
        assertEquals(MeatTypes.BEEF, updateFoodMenu.getMeatType());
        assertEquals(Vegetables.MANY, updateFoodMenu.getVegetables());
        assertNull(updateFoodMenu.getImagePath());
    }

    @Test
    void 음식메뉴_수정_음식고유번호_다름() {
        File file = mock(File.class);

        User user = userRepository.save(UserFixture.fixture().build());
        userRepository.save(user);

        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = foodMenuRepository.save(FoodMenuFixture.fixture(restaurant).build());
        foodMenuRepository.save(foodMenu);
        Long foodMenuId = foodMenu.getId();

        FoodMenuUpdateServiceDto foodMenuUpdateServiceDto = new FoodMenuUpdateServiceDto("updateTest", 3000, Temperature.HOT, FoodTypes.AMERICANO, MeatTypes.BEEF, Vegetables.MANY);
        foodMenuUpdateService.update(foodMenuId + 1L, foodMenuUpdateServiceDto, file);

        FoodMenu updateFoodMenu = foodMenuRepository.findById(foodMenuId).get();

        assertNotEquals("updateTest", updateFoodMenu.getName());
        assertNotEquals(3000, updateFoodMenu.getPrice());
        assertNotEquals(Temperature.HOT, updateFoodMenu.getTemperature());
        assertNotEquals(FoodTypes.AMERICANO, updateFoodMenu.getFoodTypes());
        assertNotEquals(MeatTypes.BEEF, updateFoodMenu.getMeatType());
        assertNotEquals(Vegetables.MANY, updateFoodMenu.getVegetables());
        assertNull(updateFoodMenu.getImagePath());
    }
}