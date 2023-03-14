package com.example.foodcloud.controller.core.point;

import com.example.foodcloud.controller.advice.ParamValidateAdvice;
import com.example.foodcloud.controller.advice.UserExceptionAdvice;
import com.example.foodcloud.controller.interceptor.LoginInterceptor;
import com.example.foodcloud.domain.point.domain.Point;
import com.example.foodcloud.domain.point.domain.PointRepository;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import com.example.foodcloud.enums.KoreanErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PointChargePostControllerTest {
    private final PointChargeController pointChargeController;
    private final PointRepository pointRepository;
    private final UserRepository userRepository;
    private final LoginInterceptor loginInterceptor;
    private final ParamValidateAdvice paramValidateAdvice;
    private MockMvc mockMvc;

    @Autowired
    public PointChargePostControllerTest(PointChargeController pointChargeController, PointRepository pointRepository, UserRepository userRepository, LoginInterceptor loginInterceptor, ParamValidateAdvice paramValidateAdvice) {
        this.pointChargeController = pointChargeController;
        this.pointRepository = pointRepository;
        this.userRepository = userRepository;
        this.loginInterceptor = loginInterceptor;
        this.paramValidateAdvice = paramValidateAdvice;
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(pointChargeController)
                .addInterceptors(loginInterceptor)
                .setControllerAdvice(paramValidateAdvice)
                .build();
    }

    @Test
    void 포인트_충전_정상작동() throws Exception {
        User user = new User("test", "test", "test");
        userRepository.save(user);

        Point point = new Point(user);
        point.updatePoint(5000);
        pointRepository.save(point);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = post("/point/charge")
                .param("point", "3000")
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/point/charge-check"))
                .andExpect(model().attribute("isCharge", true));

        assertEquals(8000, point.getTotalPoint());
        assertEquals(3000, point.getCalculationPoints());
    }

    @Test
    void 포인트_충전_유저_고유번호_다름() throws Exception {
        User user = new User("test", "test", "test");
        userRepository.save(user);

        Point point = new Point(user);
        point.updatePoint(5000);
        pointRepository.save(point);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId() + 1L);

        MockHttpServletRequestBuilder builder = post("/point/charge")
                .param("point", "3000")
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/point/charge-check"))
                .andExpect(model().attribute("isCharge", false));

        assertEquals(5000, point.getTotalPoint());
        assertEquals(5000, point.getCalculationPoints());
    }

    @Test
    void 포인트_충전_세션_없음() throws Exception {
        User user = new User("test", "test", "test");
        userRepository.save(user);

        Point point = new Point(user);
        point.updatePoint(5000);
        pointRepository.save(point);

        MockHttpServletRequestBuilder builder = post("/point/charge")
                .param("point", "3000");

        mockMvc.perform(builder)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/login"));

        assertEquals(5000, point.getTotalPoint());
        assertEquals(5000, point.getCalculationPoints());
    }

    @Test
    void 포인트_충전_금액이_0보다_작음() throws Exception {
        User user = new User("test", "test", "test");
        userRepository.save(user);

        Point point = new Point(user);
        point.updatePoint(5000);
        pointRepository.save(point);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = post("/point/charge")
                .param("point", "-3000")
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/error/error-page"))
                .andExpect(model().attribute("errorMsg", KoreanErrorCode.METHOD_ARGUMENT.getResult()));

        assertEquals(5000, point.getTotalPoint());
        assertEquals(5000, point.getCalculationPoints());
    }

    @Test
    void 포인트_충전_금액이_300만보다_큼() throws Exception {
        User user = new User("test", "test", "test");
        userRepository.save(user);

        Point point = new Point(user);
        point.updatePoint(5000);
        pointRepository.save(point);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", user.getId());

        MockHttpServletRequestBuilder builder = post("/point/charge")
                .param("point", "30000001")
                .session(session);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("thymeleaf/error/error-page"))
                .andExpect(model().attribute("errorMsg", KoreanErrorCode.METHOD_ARGUMENT.getResult()));

        assertEquals(5000, point.getTotalPoint());
        assertEquals(5000, point.getCalculationPoints());
    }
}