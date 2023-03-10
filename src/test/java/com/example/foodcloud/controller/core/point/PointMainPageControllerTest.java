package com.example.foodcloud.controller.core.point;

import com.example.foodcloud.controller.interceptor.LoginInterceptor;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.domain.point.domain.Point;
import com.example.foodcloud.domain.point.domain.PointRepository;
import com.example.foodcloud.domain.restaurant.domain.Restaurant;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PointMainPageControllerTest {
    private final PointMainPageController pointMainPageController;
    private final PointRepository pointRepository;
    private final UserRepository userRepository;
    private final LoginInterceptor loginInterceptor;
    private MockMvc mockMvc;

    @Autowired
    public PointMainPageControllerTest(PointMainPageController pointMainPageController, PointRepository pointRepository, UserRepository userRepository, LoginInterceptor loginInterceptor) {
        this.pointMainPageController = pointMainPageController;
        this.pointRepository = pointRepository;
        this.userRepository = userRepository;
        this.loginInterceptor = loginInterceptor;
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(pointMainPageController)
                .addInterceptors(loginInterceptor)
                .build();
    }

    @Test
    void ?????????_???????????????_????????????() throws Exception {
        User user = new User("test", "test", "test");
        userRepository.save(user);

        Point point = new Point(user);
        pointRepository.save(point);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = get("/point/main")
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/point/main"))
                .andExpect(model().attribute("myPoint", point));

    }

    @Test
    void ?????????_???????????????_?????????_??????() throws Exception {
        User user = new User("test", "test", "test");
        userRepository.save(user);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = get("/point/main")
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/point/main"));

        assertNotNull(pointRepository.findByUserId(user.getId()));
    }

    @Test
    void ?????????_???????????????_??????_??????() throws Exception {
        User user = new User("test", "test", "test");
        userRepository.save(user);

        Point point = new Point(user);
        pointRepository.save(point);

        MockHttpServletRequestBuilder builder = get("/point/main");

        mockMvc.perform(builder)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/login"));
    }
}