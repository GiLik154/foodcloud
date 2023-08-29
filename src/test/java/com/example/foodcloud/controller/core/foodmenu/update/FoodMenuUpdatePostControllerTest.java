package com.example.foodcloud.controller.core.foodmenu.update;

import com.example.foodcloud.FoodMenuFixture;
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
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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
class FoodMenuUpdatePostControllerTest {
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
    public FoodMenuUpdatePostControllerTest(WebApplicationContext context, FoodMenuRepository foodMenuRepository, RestaurantRepository restaurantRepository, UserRepository userRepository, ImageUploader imageUploader, FileConverter fileConverter) {
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
    void Post_음식_메뉴_업데이트_정상작동() throws Exception {
        byte[] imageBytes = "test-image".getBytes();
        String imageName = "test-image.jpg";
        MockMultipartFile mockMultipartFile = new MockMultipartFile("multipartFile", imageName, "image/jpeg", imageBytes);

        given(fileConverter.convert(any(MultipartFile.class))).willReturn(new File("testFile"));
        given(imageUploader.savedFileAndReturnFilePath(anyString(), any(File.class))).willReturn("savedFile");

        User user = userRepository.save(UserFixture.fixture().build());
        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());
        FoodMenu foodMenu = foodMenuRepository.save(FoodMenuFixture.fixture(restaurant).build());

        UserDetail userDetail = new UserDetail(user.getName(), user.getPassword(), user.getUserGrade(), user.getId());
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart("/food-menu/update/" + foodMenu.getId())
                .file(mockMultipartFile)
                .with(csrf())
                .param("name", "updateName")
                .param("price", "4444")
                .param("temperature", String.valueOf(Temperature.LUKEWARM))
                .param("foodTypes", String.valueOf(FoodTypes.RED_BEAN_BREAD))
                .param("meatType", String.valueOf(MeatTypes.LAMB))
                .param("vegetables", String.valueOf(Vegetables.MANY));

        mockMvc.perform(builder)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/food-menu/update" + foodMenu.getId()));

        verify(fileConverter, times(1)).convert(mockMultipartFile);

        assertEquals("updateName", foodMenu.getName());
        assertEquals(4444, foodMenu.getPrice());
        assertEquals(Temperature.LUKEWARM, foodMenu.getTemperature());
        assertEquals(FoodTypes.RED_BEAN_BREAD, foodMenu.getFoodTypes());
        assertEquals(MeatTypes.LAMB, foodMenu.getMeatType());
        assertEquals(Vegetables.MANY, foodMenu.getVegetables());
        assertEquals("savedFile", foodMenu.getImagePath());
    }

    @Test
    void Post_음식_메뉴_업데이트_음식메뉴_고유번호_다름() throws Exception {
        byte[] imageBytes = "test-image".getBytes();
        String imageName = "test-image.jpg";
        MockMultipartFile mockMultipartFile = new MockMultipartFile("multipartFile", imageName, "image/jpeg", imageBytes);

        given(fileConverter.convert(any(MultipartFile.class))).willReturn(new File("testFile"));
        given(imageUploader.savedFileAndReturnFilePath(anyString(), any(File.class))).willReturn("savedFile");

        User user = userRepository.save(UserFixture.fixture().build());
        Restaurant restaurant = restaurantRepository.save(RestaurantFixture.fixture(user).build());
        FoodMenu foodMenu = foodMenuRepository.save(FoodMenuFixture.fixture(restaurant).build());

        UserDetail userDetail = new UserDetail(user.getName(), user.getPassword(), user.getUserGrade(), user.getId());
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart("/food-menu/update/" + foodMenu.getId() + 1L)
                .file(mockMultipartFile)
                .with(csrf())
                .param("name", "updateName")
                .param("price", "4444")
                .param("temperature", String.valueOf(Temperature.LUKEWARM))
                .param("foodTypes", String.valueOf(FoodTypes.RED_BEAN_BREAD))
                .param("meatType", String.valueOf(MeatTypes.LAMB))
                .param("vegetables", String.valueOf(Vegetables.MANY));

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(view().name("thymeleaf/error/error-page"))
                .andExpect(model().attribute("errorMsg", KoreanErrorCode.FOOD_MENU_NOT_FOUND.getResult()));

        verify(fileConverter, times(1)).convert(mockMultipartFile);

        assertNotEquals("updateName", foodMenu.getName());
        assertNotEquals(4444, foodMenu.getPrice());
        assertNotEquals(Temperature.HOT, foodMenu.getTemperature());
        assertNotEquals(FoodTypes.SPAGHETTI, foodMenu.getFoodTypes());
        assertNotEquals(MeatTypes.NONE, foodMenu.getMeatType());
        assertNotEquals(Vegetables.MANY, foodMenu.getVegetables());
        assertNotEquals("savedFile", foodMenu.getImagePath());
    }

    @Test
    @WithAnonymousUser
    void 로그인_안하면_접속_못함() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart("/food-menu/update/" + 1L)
                .with(csrf());

        mockMvc.perform(builder)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/user/login"));
    }
}