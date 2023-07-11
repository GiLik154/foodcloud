package com.example.foodcloud.domain.foodmenu.service;

import com.example.foodcloud.FoodMenuFixture;
import com.example.foodcloud.RestaurantFixture;
import com.example.foodcloud.UserFixture;
import com.example.foodcloud.application.image.ImageUploader;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenuRepository;
import com.example.foodcloud.domain.foodmenu.service.commend.FoodMenuUpdaterCommend;
import com.example.foodcloud.domain.restaurant.domain.Restaurant;
import com.example.foodcloud.domain.restaurant.domain.RestaurantRepository;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import com.example.foodcloud.enums.foodmenu.FoodTypes;
import com.example.foodcloud.enums.foodmenu.MeatTypes;
import com.example.foodcloud.enums.foodmenu.Temperature;
import com.example.foodcloud.enums.foodmenu.Vegetables;
import com.example.foodcloud.exception.NotFoundFoodMenuException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FoodMenuUpdaterTest {
    private final FoodMenuUpdater foodMenuUpdater;
    private final FoodMenuRepository foodMenuRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    @MockBean
    private final ImageUploader imageUploader;

    @Autowired
    public FoodMenuUpdaterTest(FoodMenuUpdater foodMenuUpdater, FoodMenuRepository foodMenuRepository, RestaurantRepository restaurantRepository, UserRepository userRepository, ImageUploader imageUploader) {
        this.foodMenuUpdater = foodMenuUpdater;
        this.foodMenuRepository = foodMenuRepository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
        this.imageUploader = imageUploader;
    }

    @Test
    void 음식메뉴_수정_정상작동() {
        given(imageUploader.savedFileAndReturnFilePath(anyString(), any(File.class))).willReturn("savedFile");

        File file = new File("test.jpg");

        User user = userRepository.save(UserFixture.fixture().build());
        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());
        FoodMenu foodMenu = foodMenuRepository.save(FoodMenuFixture.fixture(restaurant).build());

        Long foodMenuId = foodMenu.getId();

        FoodMenuUpdaterCommend foodMenuUpdaterCommend = new FoodMenuUpdaterCommend("updateTest", 3000, Temperature.COLD, FoodTypes.ADE, MeatTypes.CHICKEN, Vegetables.FEW);
        foodMenuUpdater.update(foodMenuId, foodMenuUpdaterCommend, file);

        assertEquals("updateTest", foodMenu.getName());
        assertEquals(3000, foodMenu.getPrice());
        assertEquals(FoodTypes.ADE, foodMenu.getFoodTypes());
        assertEquals(Temperature.COLD, foodMenu.getTemperature());
        assertEquals(MeatTypes.CHICKEN, foodMenu.getMeatType());
        assertEquals(Vegetables.FEW, foodMenu.getVegetables());

        verify(imageUploader).savedFileAndReturnFilePath(restaurant.getName(), file);
        assertEquals("savedFile", foodMenu.getImagePath());
    }

    @Test
    void 이미지가_없을_경우_ImagePath가_null() {
        given(imageUploader.savedFileAndReturnFilePath(anyString(), any(File.class))).willReturn("savedFile");

        File file = null;

        User user = userRepository.save(UserFixture.fixture().build());
        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());
        FoodMenu foodMenu = foodMenuRepository.save(FoodMenuFixture.fixture(restaurant).build());

        Long foodMenuId = foodMenu.getId();

        FoodMenuUpdaterCommend foodMenuUpdaterCommend = new FoodMenuUpdaterCommend("updateTest", 3000, Temperature.HOT, FoodTypes.AMERICANO, MeatTypes.CHICKEN, Vegetables.MANY);
        foodMenuUpdater.update(foodMenuId, foodMenuUpdaterCommend, file);

        FoodMenu updateFoodMenu = foodMenuRepository.findById(foodMenuId).get();

        assertEquals("updateTest", updateFoodMenu.getName());
        assertEquals(3000, updateFoodMenu.getPrice());
        assertEquals(Temperature.HOT, updateFoodMenu.getTemperature());
        assertEquals(FoodTypes.AMERICANO, updateFoodMenu.getFoodTypes());
        assertEquals(MeatTypes.CHICKEN, updateFoodMenu.getMeatType());
        assertEquals(Vegetables.MANY, updateFoodMenu.getVegetables());

        verify(imageUploader, never()).savedFileAndReturnFilePath(restaurant.getName(), file);
        assertNull(updateFoodMenu.getImagePath());
    }

    @Test
    void 음식메뉴의_고유번호가_다를경우_익셉션_발생() {
        given(imageUploader.savedFileAndReturnFilePath(anyString(), any(File.class))).willReturn("savedFile");

        File file = new File("test.jpg");

        User user = userRepository.save(UserFixture.fixture().build());
        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());
        FoodMenu foodMenu = foodMenuRepository.save(FoodMenuFixture.fixture(restaurant).build());

        Long foodMenuId = foodMenu.getId();

        FoodMenuUpdaterCommend foodMenuUpdaterCommend = new FoodMenuUpdaterCommend("updateTest", 3000, Temperature.HOT, FoodTypes.AMERICANO, MeatTypes.CHICKEN, Vegetables.MANY);
        assertThrows(NotFoundFoodMenuException.class, () -> foodMenuUpdater.update(foodMenuId + 1L, foodMenuUpdaterCommend, file));

        FoodMenu updateFoodMenu = foodMenuRepository.findById(foodMenuId).get();

        assertNotEquals("updateTest", updateFoodMenu.getName());
        assertNotEquals(3000, updateFoodMenu.getPrice());
        assertNotEquals(Temperature.HOT, updateFoodMenu.getTemperature());
        assertNotEquals(FoodTypes.AMERICANO, updateFoodMenu.getFoodTypes());
        assertNotEquals(MeatTypes.CHICKEN, updateFoodMenu.getMeatType());
        assertNotEquals(Vegetables.MANY, updateFoodMenu.getVegetables());

        verify(imageUploader, never()).savedFileAndReturnFilePath(restaurant.getName(), file);
        assertNull(updateFoodMenu.getImagePath());
    }
}