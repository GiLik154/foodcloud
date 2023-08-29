package com.example.foodcloud.controller.core.foodmenu.add;

import com.example.foodcloud.RestaurantFixture;
import com.example.foodcloud.UserFixture;
import com.example.foodcloud.controller.advice.UserExceptionAdvice;
import com.example.foodcloud.controller.core.foodmenu.FoodMenuTypeRestController;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenuRepository;
import com.example.foodcloud.domain.restaurant.domain.Restaurant;
import com.example.foodcloud.domain.restaurant.domain.RestaurantRepository;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import com.example.foodcloud.enums.foodmenu.FoodTypes;
import com.example.foodcloud.enums.foodmenu.MeatTypes;
import com.example.foodcloud.enums.foodmenu.Temperature;
import com.example.foodcloud.enums.foodmenu.Vegetables;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FoodMenuAddPostControllerTest {

    private final FoodMenuTypeRestController foodMenuTypeRestController;
    private final UserExceptionAdvice userExceptionAdvice;
    private final FoodMenuRepository foodMenuRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private MockMvc mockMvc;

    @Autowired
    public FoodMenuAddPostControllerTest(FoodMenuTypeRestController foodMenuTypeRestController, UserExceptionAdvice userExceptionAdvice, FoodMenuRepository foodMenuRepository, RestaurantRepository restaurantRepository, UserRepository userRepository) {
        this.foodMenuTypeRestController = foodMenuTypeRestController;
        this.userExceptionAdvice = userExceptionAdvice;
        this.foodMenuRepository = foodMenuRepository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(foodMenuTypeRestController)
                .setControllerAdvice(userExceptionAdvice)
                .build();


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
    void 음식_메뉴_정상추가() throws Exception {
        String path = "food-menu-images/test/";
        File folder = new File(path);

        byte[] imageBytes = "test-image".getBytes();
        String imageName = "test-image.jpg";
        MockMultipartFile mockMultipartFile = new MockMultipartFile("multipartFile", imageName, "image/jpeg", imageBytes);

        User user = userRepository.save(UserFixture.fixture().build());
        userRepository.save(user);

        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());
        restaurantRepository.save(restaurant);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart("/food-menu/add/" + restaurant.getId())
                .file(mockMultipartFile)
                .param("restaurantId", String.valueOf(restaurant.getId()))
                .param("name", "testName")
                .param("price", "5000")
                .param("temperature", String.valueOf(Temperature.COLD))
                .param("foodTypes", String.valueOf(FoodTypes.ADE))
                .param("meatType", String.valueOf(MeatTypes.CHICKEN))
                .param("vegetables", String.valueOf(Vegetables.FEW))
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/food-menu/add"));

        FoodMenu foodMenu = foodMenuRepository.findByRestaurantId(restaurant.getId()).get(0);

        assertEquals(1, folder.listFiles().length);
        assertEquals("testName", foodMenu.getName());
        assertEquals(5000, foodMenu.getPrice());
        assertEquals(Temperature.COLD, foodMenu.getTemperature());
        assertEquals(FoodTypes.ADE, foodMenu.getFoodTypes());
        assertEquals(MeatTypes.CHICKEN, foodMenu.getMeatType());
        assertEquals(Vegetables.FEW, foodMenu.getVegetables());
    }

    @Test
    void 음식_메뉴_정상추가_파일_없음() throws Exception {
        User user = userRepository.save(UserFixture.fixture().build());
        userRepository.save(user);

        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());
        restaurantRepository.save(restaurant);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart("/food-menu/add/" + restaurant.getId())
                .param("restaurantId", String.valueOf(restaurant.getId()))
                .param("name", "testName")
                .param("price", "5000")
                .param("temperature", String.valueOf(Temperature.COLD))
                .param("foodTypes", String.valueOf(FoodTypes.ADE))
                .param("meatType", String.valueOf(MeatTypes.CHICKEN))
                .param("vegetables", String.valueOf(Vegetables.FEW))
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/food-menu/add"));

        FoodMenu foodMenu = foodMenuRepository.findByRestaurantId(restaurant.getId()).get(0);

        assertEquals("testName", foodMenu.getName());
        assertEquals(5000, foodMenu.getPrice());
        assertEquals(Temperature.COLD, foodMenu.getTemperature());
        assertEquals(FoodTypes.ADE, foodMenu.getFoodTypes());
        assertEquals(MeatTypes.CHICKEN, foodMenu.getMeatType());
        assertEquals(Vegetables.FEW, foodMenu.getVegetables());
    }

    @Test
    void 음식_메뉴_세션_없음() throws Exception {
        byte[] imageBytes = "test-image".getBytes();
        String imageName = "test-image.jpg";
        MockMultipartFile mockMultipartFile = new MockMultipartFile("multipartFile", imageName, "image/jpeg", imageBytes);

        User user = userRepository.save(UserFixture.fixture().build());
        userRepository.save(user);

        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());
        restaurantRepository.save(restaurant);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart("/food-menu/add/" + restaurant.getId())
                .file(mockMultipartFile)
                .param("restaurantId", String.valueOf(restaurant.getId()))
                .param("name", "testName")
                .param("price", "5000")
                .param("temperature", String.valueOf(Temperature.COLD))
                .param("foodTypes", String.valueOf(FoodTypes.ADE))
                .param("meatType", String.valueOf(MeatTypes.CHICKEN))
                .param("vegetables", String.valueOf(Vegetables.FEW));

        mockMvc.perform(builder)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/login"));
    }

    @Test
    void 음식_메뉴_유저_고유번호_다름() throws Exception {
        byte[] imageBytes = "test-image".getBytes();
        String imageName = "test-image.jpg";
        MockMultipartFile mockMultipartFile = new MockMultipartFile("multipartFile", imageName, "image/jpeg", imageBytes);

        User user = userRepository.save(UserFixture.fixture().build());
        userRepository.save(user);

        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());
        restaurantRepository.save(restaurant);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId() + 1L);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart("/food-menu/add/" + restaurant.getId())
                .file(mockMultipartFile)
                .param("restaurantId", String.valueOf(restaurant.getId()))
                .param("name", "testName")
                .param("price", "5000")
                .param("temperature", String.valueOf(Temperature.COLD))
                .param("foodTypes", String.valueOf(FoodTypes.ADE))
                .param("meatType", String.valueOf(MeatTypes.CHICKEN))
                .param("vegetables", String.valueOf(Vegetables.FEW))
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/food-menu/add"));
    }

    @Test
    void 음식_메뉴_식당_고유번호_다름() throws Exception {
        byte[] imageBytes = "test-image".getBytes();
        String imageName = "test-image.jpg";
        MockMultipartFile mockMultipartFile = new MockMultipartFile("multipartFile", imageName, "image/jpeg", imageBytes);

        User user = userRepository.save(UserFixture.fixture().build());
        userRepository.save(user);

        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());
        restaurantRepository.save(restaurant);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart("/food-menu/add/" + restaurant.getId() + 1L)
                .file(mockMultipartFile)
                .param("name", "testName")
                .param("price", "5000")
                .param("temperature", String.valueOf(Temperature.COLD))
                .param("foodTypes", String.valueOf(FoodTypes.ADE))
                .param("meatType", String.valueOf(MeatTypes.CHICKEN))
                .param("vegetables", String.valueOf(Vegetables.FEW))
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/food-menu/add"));
    }
}