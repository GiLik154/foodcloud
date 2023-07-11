package com.example.foodcloud.domain.foodmenu.service;

import com.example.foodcloud.RestaurantFixture;
import com.example.foodcloud.UserFixture;
import com.example.foodcloud.application.image.ImageUploader;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenuRepository;
import com.example.foodcloud.domain.foodmenu.service.commend.FoodMenuCreatorCommend;
import com.example.foodcloud.domain.restaurant.domain.Restaurant;
import com.example.foodcloud.domain.restaurant.domain.RestaurantRepository;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import com.example.foodcloud.enums.foodmenu.FoodTypes;
import com.example.foodcloud.enums.foodmenu.MeatTypes;
import com.example.foodcloud.enums.foodmenu.Temperature;
import com.example.foodcloud.enums.foodmenu.Vegetables;
import com.example.foodcloud.exception.NotFoundRestaurantException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FoodMenuCreatorTest {
    private final FoodMenuCreator foodMenuCreator;
    private final FoodMenuRepository foodMenuRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    @MockBean
    private final ImageUploader imageUploader;

    @Autowired
    public FoodMenuCreatorTest(FoodMenuCreator foodMenuCreator, FoodMenuRepository foodMenuRepository, RestaurantRepository restaurantRepository, UserRepository userRepository, ImageUploader imageUploader) {
        this.foodMenuCreator = foodMenuCreator;
        this.foodMenuRepository = foodMenuRepository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
        this.imageUploader = imageUploader;
    }

    @Test
    void 음식메뉴_추가_정상작동() {
        given(imageUploader.savedFileAndReturnFilePath(anyString(), any(File.class))).willReturn("savedFile");

        File file = new File("test.jpg");

        User user = userRepository.save(UserFixture.fixture().build());
        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());

        Long userId = user.getId();
        Long restaurantId = restaurant.getId();

        FoodMenuCreatorCommend foodMenuCreatorCommend = new FoodMenuCreatorCommend("test", 5000, Temperature.COLD, FoodTypes.ADE, MeatTypes.CHICKEN, Vegetables.FEW);
        foodMenuCreator.create(userId, restaurantId, foodMenuCreatorCommend, file);
        FoodMenu foodMenu = foodMenuRepository.findByRestaurantId(restaurantId).get(0);

        assertEquals("test", foodMenu.getName());
        assertEquals(5000, foodMenu.getPrice());
        assertEquals(FoodTypes.ADE, foodMenu.getFoodTypes());
        assertEquals(Temperature.COLD, foodMenu.getTemperature());
        assertEquals(MeatTypes.CHICKEN, foodMenu.getMeatType());
        assertEquals(Vegetables.FEW, foodMenu.getVegetables());

        verify(imageUploader).savedFileAndReturnFilePath(restaurant.getName(), file);
        assertEquals("savedFile", foodMenu.getImagePath());
    }

    @Test
    void 이미지가_없을_경우_ImagePath가_null() {
        File file = null;

        User user = userRepository.save(UserFixture.fixture().build());
        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());

        Long userId = user.getId();
        Long restaurantId = restaurant.getId();

        FoodMenuCreatorCommend foodMenuCreatorCommend = new FoodMenuCreatorCommend("test", 5000, Temperature.COLD, FoodTypes.ADE, MeatTypes.CHICKEN, Vegetables.FEW);
        foodMenuCreator.create(userId, restaurantId, foodMenuCreatorCommend, file);
        FoodMenu foodMenu = foodMenuRepository.findByRestaurantId(restaurantId).get(0);

        assertEquals("test", foodMenu.getName());
        assertEquals(5000, foodMenu.getPrice());
        assertEquals(FoodTypes.ADE, foodMenu.getFoodTypes());
        assertEquals(Temperature.COLD, foodMenu.getTemperature());
        assertEquals(MeatTypes.CHICKEN, foodMenu.getMeatType());
        assertEquals(Vegetables.FEW, foodMenu.getVegetables());

        verify(imageUploader, never()).savedFileAndReturnFilePath(restaurant.getName(), file);
        assertNull(foodMenu.getImagePath());
    }

    @Test
    void 음식메뉴_추가_유저고유번호_다름() {
        File file = mock(File.class);

        User user = userRepository.save(UserFixture.fixture().build());
        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());

        Long userId = user.getId();
        Long restaurantId = restaurant.getId();

        FoodMenuCreatorCommend foodMenuCreatorCommend = new FoodMenuCreatorCommend("test", 5000, Temperature.COLD, FoodTypes.ADE, MeatTypes.CHICKEN, Vegetables.FEW);
        assertThrows(NotFoundRestaurantException.class, () -> foodMenuCreator.create(userId + 1L, restaurantId, foodMenuCreatorCommend, file));

        List<FoodMenu> foodMenus = foodMenuRepository.findByRestaurantId(restaurantId);

        assertTrue(foodMenus.isEmpty());
        verify(imageUploader, never()).savedFileAndReturnFilePath(restaurant.getName(), file);
    }

    @Test
    void 음식메뉴_추가_식당고유번호_다름() {
        File file = mock(File.class);

        User user = userRepository.save(UserFixture.fixture().build());
        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());

        Long userId = user.getId();
        Long restaurantId = restaurant.getId();

        FoodMenuCreatorCommend foodMenuCreatorCommend = new FoodMenuCreatorCommend("test", 5000, Temperature.COLD, FoodTypes.ADE, MeatTypes.CHICKEN, Vegetables.FEW);

        assertThrows(NotFoundRestaurantException.class, () -> foodMenuCreator.create(userId, restaurantId + 1L, foodMenuCreatorCommend, file));

        List<FoodMenu> foodMenus = foodMenuRepository.findByRestaurantId(restaurantId);

        assertTrue(foodMenus.isEmpty());
        verify(imageUploader, never()).savedFileAndReturnFilePath(restaurant.getName(), file);
    }
}