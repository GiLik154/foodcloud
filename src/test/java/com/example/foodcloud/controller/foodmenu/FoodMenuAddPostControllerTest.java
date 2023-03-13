package com.example.foodcloud.controller.foodmenu;

import com.example.foodcloud.controller.advice.UserExceptionAdvice;
import com.example.foodcloud.controller.interceptor.LoginInterceptor;
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
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FoodMenuAddPostControllerTest {

    private final FoodMenuAddController foodMenuAddController;
    private final UserExceptionAdvice userExceptionAdvice;
    private final LoginInterceptor loginInterceptor;
    private final FoodMenuRepository foodMenuRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private MockMvc mockMvc;

    @Autowired
    public FoodMenuAddPostControllerTest(FoodMenuAddController foodMenuAddController, UserExceptionAdvice userExceptionAdvice, LoginInterceptor loginInterceptor, FoodMenuRepository foodMenuRepository, RestaurantRepository restaurantRepository, UserRepository userRepository) {
        this.foodMenuAddController = foodMenuAddController;
        this.userExceptionAdvice = userExceptionAdvice;
        this.loginInterceptor = loginInterceptor;
        this.foodMenuRepository = foodMenuRepository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(foodMenuAddController)
                .setControllerAdvice(userExceptionAdvice)
                .addInterceptors(loginInterceptor)
                .build();
    }

    @Test
    void 음식_메뉴_정상추가() throws Exception {
        byte[] imageBytes = "test-image".getBytes();
        String imageName = "test-image.jpg";
        MockMultipartFile file = new MockMultipartFile("file", imageName, "image/jpeg", imageBytes);

        User user = new User("test", "test", "test");
        userRepository.save(user);

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart("/food-menu/add")
                .file(file)
                .param("restaurantId", String.valueOf(restaurant.getId()))
                .param("name", "testName")
                .param("price", "5000")
                .param("foodType", "testType")
                .param("temperature", "testTemperature")
                .param("meatType", "testMeat")
                .param("vegetables", "testVegetables")
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/food-menu/add"))
                .andExpect(model().attribute("isAdd", true));

        FoodMenu foodMenu = foodMenuRepository.findByRestaurantId(restaurant.getId()).get(0);

        assertEquals("testName", foodMenu.getFoodMenuName());
        assertEquals(5000, foodMenu.getPrice());
        assertEquals("testType", foodMenu.getFoodType());
        assertEquals("testTemperature", foodMenu.getTemperature());
        assertEquals("testMeat", foodMenu.getMeatType());
        assertEquals("testVegetables", foodMenu.getVegetables());
    }

    @Test
    void 음식_메뉴_정상작동() throws Exception {
        byte[] imageBytes = "test-image".getBytes();
        String imageName = "test-image.jpg";
        MockMultipartFile file = new MockMultipartFile("file", imageName, "image/jpeg", imageBytes);

        User user = new User("test", "test", "test");
        userRepository.save(user);

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart("/food-menu/add")
                .file(file)
                .param("restaurantId", String.valueOf(restaurant.getId()))
                .param("name", "testName")
                .param("price", "5000")
                .param("foodType", "testType")
                .param("temperature", "testTemperature")
                .param("meatType", "testMeat")
                .param("vegetables", "testVegetables")
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/food-menu/add"))
                .andExpect(model().attribute("isAdd", true));
    }

    @Test
    void 음식_메뉴_세션_없음() throws Exception {
        byte[] imageBytes = "test-image".getBytes();
        String imageName = "test-image.jpg";
        MockMultipartFile file = new MockMultipartFile("file", imageName, "image/jpeg", imageBytes);

        User user = new User("test", "test", "test");
        userRepository.save(user);

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart("/food-menu/add")
                .file(file)
                .param("restaurantId", String.valueOf(restaurant.getId()))
                .param("name", "testName")
                .param("price", "5000")
                .param("foodType", "testType")
                .param("temperature", "testTemperature")
                .param("meatType", "testMeat")
                .param("vegetables", "testVegetables");

        mockMvc.perform(builder)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/login"));
    }

    @Test
    void 음식_메뉴_유저_고유번호_다름() throws Exception {
        byte[] imageBytes = "test-image".getBytes();
        String imageName = "test-image.jpg";
        MockMultipartFile file = new MockMultipartFile("file", imageName, "image/jpeg", imageBytes);

        User user = new User("test", "test", "test");
        userRepository.save(user);

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId() + 1L);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart("/food-menu/add")
                .file(file)
                .param("restaurantId", String.valueOf(restaurant.getId()))
                .param("name", "testName")
                .param("price", "5000")
                .param("foodType", "testType")
                .param("temperature", "testTemperature")
                .param("meatType", "testMeat")
                .param("vegetables", "testVegetables")
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/food-menu/add"))
                .andExpect(model().attribute("isAdd", false));
    }

    @Test
    void 음식_메뉴_식당_고유번호_다름() throws Exception {
        byte[] imageBytes = "test-image".getBytes();
        String imageName = "test-image.jpg";
        MockMultipartFile file = new MockMultipartFile("file", imageName, "image/jpeg", imageBytes);

        User user = new User("test", "test", "test");
        userRepository.save(user);

        Restaurant restaurant = new Restaurant("test", "test", "test", user);
        restaurantRepository.save(restaurant);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart("/food-menu/add")
                .file(file)
                .param("restaurantId", String.valueOf(restaurant.getId() + 1L))
                .param("name", "testName")
                .param("price", "5000")
                .param("foodType", "testType")
                .param("temperature", "testTemperature")
                .param("meatType", "testMeat")
                .param("vegetables", "testVegetables")
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/food-menu/add"))
                .andExpect(model().attribute("isAdd", false));
    }
}