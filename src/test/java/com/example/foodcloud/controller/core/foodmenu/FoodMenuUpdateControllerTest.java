package com.example.foodcloud.controller.core.foodmenu;

import com.example.foodcloud.controller.advice.NotFoundExceptionAdvice;
import com.example.foodcloud.controller.interceptor.LoginInterceptor;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenuRepository;
import com.example.foodcloud.domain.restaurant.domain.Restaurant;
import com.example.foodcloud.domain.restaurant.domain.RestaurantRepository;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import com.example.foodcloud.enums.KoreanErrorCode;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FoodMenuUpdateControllerTest {
    private final FoodMenuUpdateController foodMenuUpdateController;
    private final NotFoundExceptionAdvice notFoundExceptionAdvice;
    private final LoginInterceptor loginInterceptor;
    private final FoodMenuRepository foodMenuRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private MockMvc mockMvc;

    @Autowired
    public FoodMenuUpdateControllerTest(FoodMenuUpdateController foodMenuUpdateController, NotFoundExceptionAdvice notFoundExceptionAdvice, LoginInterceptor loginInterceptor, FoodMenuRepository foodMenuRepository, RestaurantRepository restaurantRepository, UserRepository userRepository) {
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

    @Test
    void 음식_메뉴_업데이트_정상작동() throws Exception {
        byte[] imageBytes = "test-image".getBytes();
        String imageName = "test-image.jpg";
        MockMultipartFile file = new MockMultipartFile("file", imageName, "image/jpeg", imageBytes);

        User user = new User("test", "test", "test");
        userRepository.save(user);

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = new FoodMenu("testName", 5000, "testType", "testTemp", "testMeat", "testVegetables", restaurant);
        foodMenuRepository.save(foodMenu);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart("/food-menu/update")
                .file(file)
                .param("foodMenuId", String.valueOf(foodMenu.getId()))
                .param("restaurantId", String.valueOf(restaurant.getId()))
                .param("name", "updateName")
                .param("price", "4444")
                .param("foodType", "updateType")
                .param("temperature", "updateTemperature")
                .param("meatType", "updateMeat")
                .param("vegetables", "updateVegetables")
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/food-menu/update"))
                .andExpect(model().attribute("isUpdate", true));

        assertEquals("updateName", foodMenu.getFoodMenuName());
        assertEquals(4444, foodMenu.getPrice());
        assertEquals("updateType", foodMenu.getFoodType());
        assertEquals("updateTemperature", foodMenu.getTemperature());
        assertEquals("updateMeat", foodMenu.getMeatType());
        assertEquals("updateVegetables", foodMenu.getVegetables());
    }

    @Test
    void 음식_메뉴_업데이트_세션_없음() throws Exception {
        byte[] imageBytes = "test-image".getBytes();
        String imageName = "test-image.jpg";
        MockMultipartFile file = new MockMultipartFile("file", imageName, "image/jpeg", imageBytes);

        User user = new User("test", "test", "test");
        userRepository.save(user);

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = new FoodMenu("testName", 5000, "testType", "testTemp", "testMeat", "testVegetables", restaurant);
        foodMenuRepository.save(foodMenu);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart("/food-menu/update")
                .file(file)
                .param("foodMenuId", String.valueOf(foodMenu.getId()))
                .param("restaurantId", String.valueOf(restaurant.getId()))
                .param("name", "updateName")
                .param("price", "4444")
                .param("foodType", "updateType")
                .param("temperature", "updateTemperature")
                .param("meatType", "updateMeat")
                .param("vegetables", "updateVegetables");

        mockMvc.perform(builder)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/login"));

        assertNotEquals("updateName", foodMenu.getFoodMenuName());
        assertNotEquals(4444, foodMenu.getPrice());
        assertNotEquals("updateType", foodMenu.getFoodType());
        assertNotEquals("updateTemperature", foodMenu.getTemperature());
        assertNotEquals("updateMeat", foodMenu.getMeatType());
        assertNotEquals("updateVegetables", foodMenu.getVegetables());
    }

    @Test
    void 음식_메뉴_업데이트_음식메뉴_고유번호_다름() throws Exception {
        byte[] imageBytes = "test-image".getBytes();
        String imageName = "test-image.jpg";
        MockMultipartFile file = new MockMultipartFile("file", imageName, "image/jpeg", imageBytes);

        User user = new User("test", "test", "test");
        userRepository.save(user);

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = new FoodMenu("testName", 5000, "testType", "testTemp", "testMeat", "testVegetables", restaurant);
        foodMenuRepository.save(foodMenu);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart("/food-menu/update")
                .file(file)
                .param("foodMenuId", String.valueOf(foodMenu.getId() + 1L))
                .param("restaurantId", String.valueOf(restaurant.getId()))
                .param("name", "updateName")
                .param("price", "4444")
                .param("foodType", "updateType")
                .param("temperature", "updateTemperature")
                .param("meatType", "updateMeat")
                .param("vegetables", "updateVegetables")
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/food-menu/update"))
                .andExpect(model().attribute("isUpdate", false));

        assertNotEquals("updateName", foodMenu.getFoodMenuName());
        assertNotEquals(4444, foodMenu.getPrice());
        assertNotEquals("updateType", foodMenu.getFoodType());
        assertNotEquals("updateTemperature", foodMenu.getTemperature());
        assertNotEquals("updateMeat", foodMenu.getMeatType());
        assertNotEquals("updateVegetables", foodMenu.getVegetables());
    }

    @Test
    void 음식_메뉴_업데이트_식당_고유번호_다름() throws Exception {
        byte[] imageBytes = "test-image".getBytes();
        String imageName = "test-image.jpg";
        MockMultipartFile file = new MockMultipartFile("file", imageName, "image/jpeg", imageBytes);

        User user = new User("test", "test", "test");
        userRepository.save(user);

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);

        FoodMenu foodMenu = new FoodMenu("testName", 5000, "testType", "testTemp", "testMeat", "testVegetables", restaurant);
        foodMenuRepository.save(foodMenu);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart("/food-menu/update")
                .file(file)
                .param("foodMenuId", String.valueOf(foodMenu.getId()))
                .param("restaurantId", String.valueOf(restaurant.getId() + 1L))
                .param("name", "updateName")
                .param("price", "4444")
                .param("foodType", "updateType")
                .param("temperature", "updateTemperature")
                .param("meatType", "updateMeat")
                .param("vegetables", "updateVegetables")
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/error/error-page"))
                .andExpect(model().attribute("errorMsg", KoreanErrorCode.NOT_FOUND_RESTAURANT.getResult()));

        assertNotEquals("updateName", foodMenu.getFoodMenuName());
        assertNotEquals(4444, foodMenu.getPrice());
        assertNotEquals("updateType", foodMenu.getFoodType());
        assertNotEquals("updateTemperature", foodMenu.getTemperature());
        assertNotEquals("updateMeat", foodMenu.getMeatType());
        assertNotEquals("updateVegetables", foodMenu.getVegetables());
    }
}