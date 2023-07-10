package com.example.foodcloud.domain.foodmenu.service.image;

import com.example.foodcloud.FoodMenuFixture;
import com.example.foodcloud.RestaurantFixture;
import com.example.foodcloud.UserFixture;
import com.example.foodcloud.application.image.ImageUploader;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenuRepository;
import com.example.foodcloud.domain.restaurant.domain.Restaurant;
import com.example.foodcloud.domain.restaurant.domain.RestaurantRepository;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ImageUploaderImplTest {
    private final ImageUploader imageUploader;
    private final FoodMenuRepository foodMenuRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    @Autowired
    public ImageUploaderImplTest(ImageUploader imageUploader, FoodMenuRepository foodMenuRepository, RestaurantRepository restaurantRepository, UserRepository userRepository) {
        this.imageUploader = imageUploader;
        this.foodMenuRepository = foodMenuRepository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    @BeforeEach
    public void deleteFile() throws IOException {
        String path = "food-menu-images/test/";
        File folder = new File(path);

        Path uploadPath = Paths.get(path);
        Files.createDirectories(uploadPath);
        File[] deleteFolderList = folder.listFiles();

        for (File f : deleteFolderList) {
            f.delete();
        }

        folder.delete();
    }

    @Test
    void 이미지_업로드_정상작동() throws IOException {
        String path = "food-menu-images/test/";
        File folder = new File(path);
        Path uploadPath = Paths.get("food-menu-images/test/");
        Files.createDirectories(uploadPath);

        File file = new File("test.jpg");
        file.createNewFile();

        User user = userRepository.save(UserFixture.fixture().build());
        userRepository.save(user);

        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = foodMenuRepository.save(FoodMenuFixture.fixture(restaurant).build());
        foodMenuRepository.save(foodMenu);

        imageUploader.savedFileAndReturnFilePath(restaurant.getName(), file);

        assertEquals(1, folder.listFiles().length);
    }

    @Test
    void 이미지_업로드_정상작동_디렉토리없음() throws IOException {
        String path = "food-menu-images/test/";
        File folder = new File(path);

        Path uploadPath = Paths.get(path);
        Files.createDirectories(uploadPath);
        File[] deleteFolderList = folder.listFiles();

        for (File f : deleteFolderList) {
            f.delete();
        }

        folder.delete();

        File file = new File("test.jpg");
        file.createNewFile();

        User user = userRepository.save(UserFixture.fixture().build());
        userRepository.save(user);

        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = foodMenuRepository.save(FoodMenuFixture.fixture(restaurant).build());
        foodMenuRepository.save(foodMenu);

        imageUploader.savedFileAndReturnFilePath(restaurant.getName(), file);

        assertEquals(1, folder.listFiles().length);
    }
}