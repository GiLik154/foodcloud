package com.example.foodcloud.controller.core.point;

import com.example.foodcloud.UserFixture;
import com.example.foodcloud.domain.payment.domain.Point;
import com.example.foodcloud.domain.payment.domain.PointRepository;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import com.example.foodcloud.enums.KoreanErrorCode;
import com.example.foodcloud.security.login.UserDetail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PointChargePostControllerTest {
    private final WebApplicationContext context;
    private final PointRepository pointRepository;
    private final UserRepository userRepository;
    private MockMvc mockMvc;

    @Autowired
    public PointChargePostControllerTest(WebApplicationContext context, PointRepository pointRepository, UserRepository userRepository) {
        this.context = context;
        this.pointRepository = pointRepository;
        this.userRepository = userRepository;
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    void Post_포인트_충전_정상작동() throws Exception {
        User user = userRepository.save(UserFixture.fixture().build());

        UserDetail userDetail = new UserDetail(user.getName(), user.getPassword(), user.getUserGrade(), user.getId());
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Point point = new Point(user);
        point.update(5000);
        pointRepository.save(point);

        MockHttpServletRequestBuilder builder = put("/point/charge")
                .with(csrf())
                .param("point", "3000");

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(view().name("thymeleaf/point/charge-check"));

        assertEquals(8000, point.getTotalPoint());
        assertEquals(3000, point.getRecentPoint());
    }

    @Test
    void Post_포인트_충전_금액이_0보다_작음() throws Exception {
        User user = userRepository.save(UserFixture.fixture().build());

        UserDetail userDetail = new UserDetail(user.getName(), user.getPassword(), user.getUserGrade(), user.getId());
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Point point = new Point(user);
        point.update(5000);
        pointRepository.save(point);

        MockHttpServletRequestBuilder builder = put("/point/charge")
                .with(csrf())
                .param("point", "-3000");

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(view().name("thymeleaf/error/error-page"))
                .andExpect(model().attribute("errorMsg", KoreanErrorCode.METHOD_ARGUMENT.getResult()));

        assertEquals(5000, point.getTotalPoint());
        assertEquals(5000, point.getRecentPoint());
    }

    @Test
    void Post_포인트_충전_금액이_300만보다_큼() throws Exception {
        User user = userRepository.save(UserFixture.fixture().build());

        UserDetail userDetail = new UserDetail(user.getName(), user.getPassword(), user.getUserGrade(), user.getId());
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Point point = new Point(user);
        point.update(5000);
        pointRepository.save(point);

        MockHttpServletRequestBuilder builder = put("/point/charge")
                .with(csrf())
                .param("point", "30000001");

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(view().name("thymeleaf/error/error-page"))
                .andExpect(model().attribute("errorMsg", KoreanErrorCode.METHOD_ARGUMENT.getResult()));

        assertEquals(5000, point.getTotalPoint());
        assertEquals(5000, point.getRecentPoint());
    }

    @Test
    @WithAnonymousUser
    void 로그인_안하면_접속_못함() throws Exception {
        MockHttpServletRequestBuilder builder = put("/point/charge")
                .with(csrf());

        mockMvc.perform(builder)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/user/login"));
    }
}