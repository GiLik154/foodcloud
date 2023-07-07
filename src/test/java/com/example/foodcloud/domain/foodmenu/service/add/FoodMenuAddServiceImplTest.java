package com.example.foodcloud.domain.foodmenu.service.add;

import com.example.foodcloud.RestaurantFixture;
import com.example.foodcloud.UserFixture;
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
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;

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
        File file = new File("test.jpg");

        User user = userRepository.save(UserFixture.fixture().build());
        userRepository.save(user);
        Long userId = user.getId();

        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());
        restaurantRepository.save(restaurant);
        Long restaurantId = restaurantRepository.findByUserId(userId).get(0).getId();

        FoodMenuAddServiceDto foodMenuAddServiceDto = new FoodMenuAddServiceDto("test", 5000, Temperature.COLD, FoodTypes.ADE, MeatTypes.CHICKEN, Vegetables.FEW);
        foodMenuAddService.add(userId, restaurantId, foodMenuAddServiceDto, file);
        FoodMenu foodMenu = foodMenuRepository.findByRestaurantId(restaurantId).get(0);

        assertEquals("test", foodMenu.getName());
        assertEquals(5000, foodMenu.getPrice());
        assertEquals(FoodTypes.ADE, foodMenu.getFoodTypes());
        assertEquals(Temperature.COLD, foodMenu.getTemperature());
        assertEquals(MeatTypes.CHICKEN, foodMenu.getMeatType());
        assertEquals(Vegetables.FEW, foodMenu.getVegetables());
        assertNotNull(foodMenu.getImagePath());
    }

    @Test
    void 음식메뉴_추가_이미지없음() {
        File file = null;

        User user = userRepository.save(UserFixture.fixture().build());
        userRepository.save(user);
        Long userId = user.getId();

        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());
        restaurantRepository.save(restaurant);
        Long restaurantId = restaurantRepository.findByUserId(userId).get(0).getId();

        FoodMenuAddServiceDto foodMenuAddServiceDto = new FoodMenuAddServiceDto("test", 5000, Temperature.COLD, FoodTypes.ADE, MeatTypes.CHICKEN, Vegetables.FEW);
        foodMenuAddService.add(userId, restaurantId, foodMenuAddServiceDto, file);
        FoodMenu foodMenu = foodMenuRepository.findByRestaurantId(restaurantId).get(0);

        assertEquals("test", foodMenu.getName());
        assertEquals(5000, foodMenu.getPrice());
        assertEquals(FoodTypes.ADE, foodMenu.getFoodTypes());
        assertEquals(Temperature.COLD, foodMenu.getTemperature());
        assertEquals(MeatTypes.CHICKEN, foodMenu.getMeatType());
        assertEquals(Vegetables.FEW, foodMenu.getVegetables());
        assertNull(foodMenu.getImagePath());
    }

    @Test
    void 음식메뉴_추가_유저고유번호_다름() {
        File file = mock(File.class);

        User user = userRepository.save(UserFixture.fixture().build());
        userRepository.save(user);
        Long userId = user.getId();

        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());
        restaurantRepository.save(restaurant);
        Long restaurantId = restaurantRepository.findByUserId(userId).get(0).getId();

        FoodMenuAddServiceDto foodMenuAddServiceDto = new FoodMenuAddServiceDto("test", 5000, Temperature.COLD, FoodTypes.ADE, MeatTypes.CHICKEN, Vegetables.FEW);
        foodMenuAddService.add(userId + 1L, restaurantId, foodMenuAddServiceDto, file);

        List<FoodMenu> foodMenus = foodMenuRepository.findByRestaurantId(restaurantId);

        assertTrue(foodMenus.isEmpty());
    }

    @Test
    void 음식메뉴_추가_식당고유번호_다름() {
        File file = mock(File.class);

        User user = userRepository.save(UserFixture.fixture().build());
        userRepository.save(user);
        Long userId = user.getId();

        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());
        restaurantRepository.save(restaurant);
        Long restaurantId = restaurantRepository.findByUserId(userId).get(0).getId();

        FoodMenuAddServiceDto foodMenuAddServiceDto = new FoodMenuAddServiceDto("test", 5000, Temperature.COLD, FoodTypes.ADE, MeatTypes.CHICKEN, Vegetables.FEW);
        foodMenuAddService.add(userId, restaurantId + 1L, foodMenuAddServiceDto, file);

        List<FoodMenu> foodMenus = foodMenuRepository.findByRestaurantId(restaurantId);

        assertTrue(foodMenus.isEmpty());
    }
}