package com.example.foodcloud.domain.payment.point.service.award;

import com.example.foodcloud.domain.payment.point.domain.Point;
import com.example.foodcloud.domain.payment.point.domain.PointRepository;
import com.example.foodcloud.domain.payment.point.service.PointRegister;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PointRegisterImplTest {
    private final PointRegister pointRegister;
    private final PointRepository pointRepository;
    private final UserRepository userRepository;

    @Autowired
    PointRegisterImplTest(PointRegister pointRegister, PointRepository pointRepository, UserRepository userRepository) {
        this.pointRegister = pointRegister;
        this.pointRepository = pointRepository;
        this.userRepository = userRepository;
    }

    @Test
    void 포인트_가입_정상작동() {
        User user = new User("test", "test", "tset");
        userRepository.save(user);

        pointRegister.award(user.getId());
        Point point = pointRepository.findByUserId(user.getId()).get();

        assertNotNull(point.getId());
        assertThat(point.getUser()).isEqualTo(user);
    }

    @Test
    void 포인트_가입_아이디_없음() {
        User user = new User("test", "test", "tset");
        userRepository.save(user);

        Long id = userRepository.getValidById("test").getId();

        UsernameNotFoundException e = assertThrows(UsernameNotFoundException.class, () ->
                pointRegister.award(id + 1L)
        );

        assertEquals("Invalid user", e.getMessage());
        assertThat(pointRepository.existsById(id)).isFalse();
    }
}