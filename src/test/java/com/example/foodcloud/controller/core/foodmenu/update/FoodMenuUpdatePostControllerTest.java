package com.example.foodcloud.controller.core.foodmenu.update;

import com.example.foodcloud.FoodMenuFixture;
import com.example.foodcloud.RestaurantFixture;
import com.example.foodcloud.UserFixture;
import com.example.foodcloud.controller.advice.NotFoundExceptionAdvice;
import com.example.foodcloud.controller.core.foodmenu.FoodMenuUpdateController;
import com.example.foodcloud.controller.interceptor.LoginInterceptor;
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
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FoodMenuUpdatePostControllerTest {
    private final FoodMenuUpdateController foodMenuUpdateController;
    private final NotFoundExceptionAdvice notFoundExceptionAdvice;
    private final LoginInterceptor loginInterceptor;
    private final FoodMenuRepository foodMenuRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private MockMvc mockMvc;

    @Autowired
    public FoodMenuUpdatePostControllerTest(FoodMenuUpdateController foodMenuUpdateController, NotFoundExceptionAdvice notFoundExceptionAdvice, LoginInterceptor loginInterceptor, FoodMenuRepository foodMenuRepository, RestaurantRepository restaurantRepository, UserRepository userRepository) {
        this.foodMenuUpdateController = foodMenuUpdateController;
        this.notFoundExceptionAdvice = notFoundExceptionAdvice;
        this.loginInterceptor = loginInterceptor;
        this.foodMenuRepository = foodMenuRepository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(foodMenuUpdateController)
                .setControllerAdvice(notFoundExceptionAdvice)
                .addInterceptors(loginInterceptor)
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
    void Post_음식_메뉴_업데이트_정상작동() throws Exception {
        byte[] imageBytes = "test-image".getBytes();
        String imageName = "test-image.jpg";
        MockMultipartFile mockMultipartFile = new MockMultipartFile("multipartFile", imageName, "image/jpeg", imageBytes);

        User user = userRepository.save(UserFixture.fixture().build());
        userRepository.save(user);

        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = foodMenuRepository.save(FoodMenuFixture.fixture(restaurant).build());
        foodMenuRepository.save(foodMenu);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart("/food-menu/update/" + foodMenu.getId())
                .file(mockMultipartFile)
                .param("restaurantId", String.valueOf(restaurant.getId()))
                .param("name", "updateName")
                .param("price", "4444")
                .param("temperature", String.valueOf(Temperature.LUKEWARM))
                .param("foodTypes", String.valueOf(FoodTypes.RED_BEAN_BREAD))
                .param("meatType", String.valueOf(MeatTypes.LAMB))
                .param("vegetables", String.valueOf(Vegetables.MANY))
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/food-menu/update"));

        assertEquals("updateName", foodMenu.getName());
        assertEquals(4444, foodMenu.getPrice());
        assertEquals(Temperature.LUKEWARM, foodMenu.getTemperature());
        assertEquals(FoodTypes.RED_BEAN_BREAD, foodMenu.getFoodTypes());
        assertEquals(MeatTypes.LAMB, foodMenu.getMeatType());
        assertEquals(Vegetables.MANY, foodMenu.getVegetables());
    }

    @Test
    void Post_음식_메뉴_업데이트_세션_없음() throws Exception {
        byte[] imageBytes = "test-image".getBytes();
        String imageName = "test-image.jpg";
        MockMultipartFile file = new MockMultipartFile("file", imageName, "image/jpeg", imageBytes);

        User user = userRepository.save(UserFixture.fixture().build());
        userRepository.save(user);

        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = foodMenuRepository.save(FoodMenuFixture.fixture(restaurant).build());
        foodMenuRepository.save(foodMenu);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart("/food-menu/update/" + foodMenu.getId())
                .file(file)
                .param("restaurantId", String.valueOf(restaurant.getId()))
                .param("name", "updateName")
                .param("price", "4444")
                .param("temperature", String.valueOf(Temperature.HOT))
                .param("foodTypes", String.valueOf(FoodTypes.SPAGHETTI))
                .param("meatType", String.valueOf(MeatTypes.NONE))
                .param("vegetables", String.valueOf(Vegetables.MANY));

        mockMvc.perform(builder)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/login"));

        assertNotEquals("updateName", foodMenu.getName());
        assertNotEquals(4444, foodMenu.getPrice());
        assertNotEquals(Temperature.HOT, foodMenu.getTemperature());
        assertNotEquals(FoodTypes.SPAGHETTI, foodMenu.getFoodTypes());
        assertNotEquals(MeatTypes.NONE, foodMenu.getMeatType());
        assertNotEquals(Vegetables.MANY, foodMenu.getVegetables());
    }

    @Test
    void Post_음식_메뉴_업데이트_음식메뉴_고유번호_다름() throws Exception {
        byte[] imageBytes = "test-image".getBytes();
        String imageName = "test-image.jpg";
        MockMultipartFile mockMultipartFile = new MockMultipartFile("multipartFile", imageName, "image/jpeg", imageBytes);

        User user = userRepository.save(UserFixture.fixture().build());
        userRepository.save(user);

        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = foodMenuRepository.save(FoodMenuFixture.fixture(restaurant).build());
        foodMenuRepository.save(foodMenu);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart("/food-menu/update/" + foodMenu.getId() + 1L)
                .file(mockMultipartFile)
                .param("restaurantId", String.valueOf(restaurant.getId()))
                .param("name", "updateName")
                .param("price", "4444")
                .param("temperature", String.valueOf(Temperature.COLD))
                .param("foodTypes", String.valueOf(FoodTypes.ADE))
                .param("meatType", String.valueOf(MeatTypes.CHICKEN))
                .param("vegetables", String.valueOf(Vegetables.FEW))
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/food-menu/update"));

        assertNotEquals("updateName", foodMenu.getName());
        assertNotEquals(4444, foodMenu.getPrice());
        assertNotEquals(Temperature.HOT, foodMenu.getTemperature());
        assertNotEquals(FoodTypes.SPAGHETTI, foodMenu.getFoodTypes());
        assertNotEquals(MeatTypes.NONE, foodMenu.getMeatType());
        assertNotEquals(Vegetables.MANY, foodMenu.getVegetables());
    }
}