package com.example.foodcloud.controller.core.foodmenu.add;

import com.example.foodcloud.RestaurantFixture;
import com.example.foodcloud.UserFixture;
import com.example.foodcloud.application.file.FileConverter;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenuRepository;
import com.example.foodcloud.domain.restaurant.domain.Restaurant;
import com.example.foodcloud.domain.restaurant.domain.RestaurantRepository;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import com.example.foodcloud.enums.KoreanErrorCode;
import com.example.foodcloud.enums.foodmenu.FoodTypes;
import com.example.foodcloud.enums.foodmenu.MeatTypes;
import com.example.foodcloud.enums.foodmenu.Temperature;
import com.example.foodcloud.enums.foodmenu.Vegetables;
import com.example.foodcloud.infra.ImageUploader;
import com.example.foodcloud.security.login.UserDetail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FoodMenuCreatePostControllerTest {
    private final WebApplicationContext context;
    private final FoodMenuRepository foodMenuRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private MockMvc mockMvc;

    @MockBean
    private final ImageUploader imageUploader;
    @MockBean
    private final FileConverter fileConverter;

    @Autowired
    public FoodMenuCreatePostControllerTest(WebApplicationContext context, FoodMenuRepository foodMenuRepository, RestaurantRepository restaurantRepository, UserRepository userRepository, ImageUploader imageUploader, FileConverter fileConverter) {
        this.context = context;
        this.foodMenuRepository = foodMenuRepository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
        this.imageUploader = imageUploader;
        this.fileConverter = fileConverter;
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    void 음식_메뉴_정상추가() throws Exception {
        byte[] imageBytes = "test-image".getBytes();
        String imageName = "test-image.jpg";
        MockMultipartFile mockMultipartFile = new MockMultipartFile("multipartFile", imageName, "image/jpeg", imageBytes);

        given(fileConverter.convert(any(MultipartFile.class))).willReturn(new File("testFile"));
        given(imageUploader.savedFileAndReturnFilePath(anyString(), any(File.class))).willReturn("savedFile");

        User user = userRepository.save(UserFixture.fixture().build());
        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());

        UserDetail userDetail = new UserDetail(user.getName(), user.getPassword(), user.getUserGrade(), user.getId());
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart("/food-menu/" + restaurant.getId())
                .file(mockMultipartFile)
                .with(csrf())
                .param("name", "testName")
                .param("price", "5000")
                .param("temperature", String.valueOf(Temperature.COLD))
                .param("foodTypes", String.valueOf(FoodTypes.ADE))
                .param("meatType", String.valueOf(MeatTypes.CHICKEN))
                .param("vegetables", String.valueOf(Vegetables.FEW));

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(view().name("thymeleaf/food-menu/add"));

        FoodMenu foodMenu = foodMenuRepository.findByRestaurantId(restaurant.getId()).get(0);
        verify(fileConverter, times(1)).convert(mockMultipartFile);

        assertEquals("testName", foodMenu.getName());
        assertEquals(5000, foodMenu.getPrice());
        assertEquals(Temperature.COLD, foodMenu.getTemperature());
        assertEquals(FoodTypes.ADE, foodMenu.getFoodTypes());
        assertEquals(MeatTypes.CHICKEN, foodMenu.getMeatType());
        assertEquals(Vegetables.FEW, foodMenu.getVegetables());
        assertEquals("savedFile", foodMenu.getImagePath());
    }

    @Test
    void 음식_메뉴_정상추가_파일_없음() throws Exception {
        User user = userRepository.save(UserFixture.fixture().build());
        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());

        UserDetail userDetail = new UserDetail(user.getName(), user.getPassword(), user.getUserGrade(), user.getId());
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart("/food-menu/" + restaurant.getId())
                .with(csrf())
                .param("name", "testName")
                .param("price", "5000")
                .param("temperature", String.valueOf(Temperature.COLD))
                .param("foodTypes", String.valueOf(FoodTypes.ADE))
                .param("meatType", String.valueOf(MeatTypes.CHICKEN))
                .param("vegetables", String.valueOf(Vegetables.FEW));

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(view().name("thymeleaf/food-menu/add"));

        FoodMenu foodMenu = foodMenuRepository.findByRestaurantId(restaurant.getId()).get(0);

        assertEquals("testName", foodMenu.getName());
        assertEquals(5000, foodMenu.getPrice());
        assertEquals(Temperature.COLD, foodMenu.getTemperature());
        assertEquals(FoodTypes.ADE, foodMenu.getFoodTypes());
        assertEquals(MeatTypes.CHICKEN, foodMenu.getMeatType());
        assertEquals(Vegetables.FEW, foodMenu.getVegetables());
    }

    @Test
    void 음식_메뉴_유저_고유번호_다름() throws Exception {
        byte[] imageBytes = "test-image".getBytes();
        String imageName = "test-image.jpg";
        MockMultipartFile mockMultipartFile = new MockMultipartFile("multipartFile", imageName, "image/jpeg", imageBytes);

        User user = userRepository.save(UserFixture.fixture().build());
        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());

        UserDetail userDetail = new UserDetail(user.getName(), user.getPassword(), user.getUserGrade(), user.getId() + 1L);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart("/food-menu/" + restaurant.getId())
                .file(mockMultipartFile)
                .with(csrf())
                .param("name", "testName")
                .param("price", "5000")
                .param("temperature", String.valueOf(Temperature.COLD))
                .param("foodTypes", String.valueOf(FoodTypes.ADE))
                .param("meatType", String.valueOf(MeatTypes.CHICKEN))
                .param("vegetables", String.valueOf(Vegetables.FEW));

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(view().name("thymeleaf/error/error-page"))
                .andExpect(model().attribute("errorMsg", KoreanErrorCode.RESTAURANT_NOT_FOUND.getResult()));
    }

    @Test
    void 음식_메뉴_식당_고유번호_다름() throws Exception {
        byte[] imageBytes = "test-image".getBytes();
        String imageName = "test-image.jpg";
        MockMultipartFile mockMultipartFile = new MockMultipartFile("multipartFile", imageName, "image/jpeg", imageBytes);

        User user = userRepository.save(UserFixture.fixture().build());
        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());

        UserDetail userDetail = new UserDetail(user.getName(), user.getPassword(), user.getUserGrade(), user.getId());
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart("/food-menu/" + restaurant.getId() + 1L)
                .file(mockMultipartFile)
                .with(csrf())
                .param("name", "testName")
                .param("price", "5000")
                .param("temperature", String.valueOf(Temperature.COLD))
                .param("foodTypes", String.valueOf(FoodTypes.ADE))
                .param("meatType", String.valueOf(MeatTypes.CHICKEN))
                .param("vegetables", String.valueOf(Vegetables.FEW));

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(view().name("thymeleaf/error/error-page"))
                .andExpect(model().attribute("errorMsg", KoreanErrorCode.RESTAURANT_NOT_FOUND.getResult()));
    }
}