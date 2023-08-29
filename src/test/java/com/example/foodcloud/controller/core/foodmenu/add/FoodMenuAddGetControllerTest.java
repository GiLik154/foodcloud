package com.example.foodcloud.controller.core.foodmenu.add;

import com.example.foodcloud.controller.advice.UserExceptionAdvice;
import com.example.foodcloud.controller.core.foodmenu.FoodMenuTypeRestController;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FoodMenuAddGetControllerTest {
    private final FoodMenuTypeRestController foodMenuTypeRestController;
    private final UserExceptionAdvice userExceptionAdvice;
    private final UserRepository userRepository;
    private MockMvc mockMvc;

    @Autowired
    public FoodMenuAddGetControllerTest(FoodMenuTypeRestController foodMenuTypeRestController, UserExceptionAdvice userExceptionAdvice, UserRepository userRepository) {
        this.foodMenuTypeRestController = foodMenuTypeRestController;
        this.userExceptionAdvice = userExceptionAdvice;
        this.userRepository = userRepository;
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(foodMenuTypeRestController)
                .setControllerAdvice(userExceptionAdvice)
                .build();
    }

    @Test
    void Get_음식메뉴_추가_정상출력() throws Exception {
        User user = new User("testName", "testPassword", "testPhone");
        userRepository.save(user);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = get("/food-menu/add")
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/food-menu/add"));
    }

    @Test
    void Get_음식메뉴_추가_세션_없음() throws Exception {
        MockHttpServletRequestBuilder builder = get("/food-menu/add");

        mockMvc.perform(builder)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/login"));
    }
}