package com.example.foodcloud.domain.foodmenu.service.image;

import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenuRepository;
import com.example.foodcloud.domain.foodmenu.service.dto.FoodMenuDto;
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
class ImageUploadServiceImplTest {
    private final ImageUploadService imageUploadService;
    private final FoodMenuRepository foodMenuRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    @Autowired
    public ImageUploadServiceImplTest(ImageUploadService imageUploadService, FoodMenuRepository foodMenuRepository, RestaurantRepository restaurantRepository, UserRepository userRepository) {
        this.imageUploadService = imageUploadService;
        this.foodMenuRepository = foodMenuRepository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    @Test
    void upload() {
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

        imageUploadService.upload(restaurantId, file, foodMenu);

        assertNotNull(foodMenu.getImagePath());
    }
}