package com.example.foodcloud.domain.user.service;

import com.example.foodcloud.domain.payment.domain.Point;
import com.example.foodcloud.domain.payment.domain.PointRepository;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import com.example.foodcloud.domain.user.service.UserRegister;
import com.example.foodcloud.exception.UserNameDuplicateException;
import com.example.foodcloud.domain.user.service.commend.UserJoinerCommend;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserServiceRegisterTest {
    private final UserRegister userRegister;
    private final UserRepository userRepository;
    private final PointRepository pointRepository;

    @Autowired
    UserServiceRegisterTest(UserRegister userRegister, UserRepository userRepository, PointRepository pointRepository) {
        this.userRegister = userRegister;
        this.userRepository = userRepository;
        this.pointRepository = pointRepository;
    }

    @Test
    void 유저_회원가입_정상작동() {
        UserJoinerCommend userJoinerCommend = new UserJoinerCommend("testName", "testPassword", "testPhone");
        userRegister.register(userJoinerCommend);

        User user = userRepository.findByName("testName").get();
        Point point = pointRepository.findByUserId(user.getId()).get();

        assertEquals("testName", user.getName());
        assertNotEquals("testPassword", user.getPassword());
        assertEquals("testPhone", user.getPhone());
        assertNotNull(point.getId());
        assertEquals(user, point.getUser());
        assertEquals(0, point.getTotalPoint());
    }

    @Test
    void 유저_회원가입_중복아이디() {
        UserJoinerCommend userJoinerCommend = new UserJoinerCommend("testName", "testPassword", "testPhone");
        UserJoinerCommend userJoinerCommend2 = new UserJoinerCommend("testName", "testPassword", "testPhone");
        userRegister.register(userJoinerCommend);

        UserNameDuplicateException e = assertThrows(UserNameDuplicateException.class, () ->
                userRegister.register(userJoinerCommend2)
        );

        assertEquals("Duplicate User Name", e.getMessage());
    }
}