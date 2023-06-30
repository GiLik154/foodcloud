package com.example.foodcloud.domain.user.service.join;

import com.example.foodcloud.domain.payment.domain.Point;
import com.example.foodcloud.domain.payment.domain.PointRepository;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import com.example.foodcloud.exception.UserNameDuplicateException;
import com.example.foodcloud.domain.user.service.join.dto.UserJoinServiceDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserJoinServiceImplTest {
    private final UserJoinService userJoinService;
    private final UserRepository userRepository;
    private final PointRepository pointRepository;

    @Autowired
    UserJoinServiceImplTest(UserJoinService userJoinService, UserRepository userRepository, PointRepository pointRepository) {
        this.userJoinService = userJoinService;
        this.userRepository = userRepository;
        this.pointRepository = pointRepository;
    }

    @Test
    void 유저_회원가입_정상작동() {
        UserJoinServiceDto userJoinServiceDto = new UserJoinServiceDto("testName", "testPassword", "testPhone");
        userJoinService.join(userJoinServiceDto);

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
        UserJoinServiceDto userJoinServiceDto = new UserJoinServiceDto("testName", "testPassword", "testPhone");
        UserJoinServiceDto userJoinServiceDto2 = new UserJoinServiceDto("testName", "testPassword", "testPhone");
        userJoinService.join(userJoinServiceDto);

        UserNameDuplicateException e = assertThrows(UserNameDuplicateException.class, () ->
                userJoinService.join(userJoinServiceDto2)
        );

        assertEquals("Duplicate User Name", e.getMessage());
    }
}