package com.example.foodcloud.domain.user.service;

import com.example.foodcloud.domain.payment.domain.Point;
import com.example.foodcloud.domain.payment.domain.PointRepository;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import com.example.foodcloud.exception.UserNameDuplicateException;
import com.example.foodcloud.domain.user.service.commend.UserRegisterCommend;
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
        UserRegisterCommend userRegisterCommend = new UserRegisterCommend("testName", "testPassword", "testPhone");
        userRegister.register(userRegisterCommend);

        User user = userRepository.findByName("testName").get();
        Point point = pointRepository.findByUserId(user.getId()).get();

        assertEquals(userRegisterCommend.getUsername(), user.getName());
        assertNotEquals(userRegisterCommend.getPassword(), user.getPassword());
        assertEquals(userRegisterCommend.getPhone(), user.getPhone());

        assertNotNull(point.getId());
        assertEquals(user, point.getUser());
        assertEquals(0, point.getTotalPoint());
    }

    @Test
    void 아이디가_중복되면_익셉션이_발생함() {
        UserRegisterCommend originName = new UserRegisterCommend("testName", "testPassword", "testPhone");
        UserRegisterCommend duplicateName = new UserRegisterCommend("testName", "testPassword", "testPhone");
        userRegister.register(originName);

        assertThrows(UserNameDuplicateException.class, () -> userRegister.register(duplicateName));
    }
}