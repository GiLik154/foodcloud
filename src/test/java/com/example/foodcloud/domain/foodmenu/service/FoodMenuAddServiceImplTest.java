package com.example.foodcloud.domain.foodmenu.service;

import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenuRepository;
import com.example.foodcloud.domain.foodmenu.service.dto.FoodMenuAddDto;
import com.example.foodcloud.domain.restaurant.domain.Restaurant;
import com.example.foodcloud.domain.restaurant.domain.RestaurantRepository;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
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
    void 메뉴_추가_정상작동() {
        byte[] imageBytes = "test-image".getBytes();
        String imageName = "test-image.jpg";
        MockMultipartFile file = new MockMultipartFile("file", imageName, "image/jpeg", imageBytes);

        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);
        Long restaurantId = restaurantRepository.findByUserId(userId).get(0).getId();

        FoodMenuAddDto foodMenuAddDto = new FoodMenuAddDto("test", 5000, "test", "test", "test", "test");
        boolean isAdd = foodMenuAddService.add(userId, restaurantId, foodMenuAddDto, file);
        FoodMenu foodMenu = foodMenuRepository.findByRestaurantId(restaurantId).get(0);

        assertTrue(isAdd);
        assertEquals("test", foodMenu.getFoodMenuName());
        assertEquals(5000, foodMenu.getPrice());
        assertEquals("test", foodMenu.getFoodType());
        assertEquals("test", foodMenu.getTemperature());
        assertEquals("test", foodMenu.getMeatType());
        assertEquals("test", foodMenu.getVegetables());
        assertNotNull(foodMenu.getImagePath());
    } //todo 요기부터 해야함
}