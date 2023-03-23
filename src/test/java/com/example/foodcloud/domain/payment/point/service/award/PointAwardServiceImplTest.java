package com.example.foodcloud.domain.payment.point.service.award;

import com.example.foodcloud.domain.payment.point.domain.Point;
import com.example.foodcloud.domain.payment.point.domain.PointRepository;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import com.example.foodcloud.exception.NotEnoughPointException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PointAwardServiceImplTest {
    private final PointAwardService pointAwardService;
    private final PointRepository pointRepository;
    private final UserRepository userRepository;

    @Autowired
    PointAwardServiceImplTest(PointAwardService pointAwardService, PointRepository pointRepository, UserRepository userRepository) {
        this.pointAwardService = pointAwardService;
        this.pointRepository = pointRepository;
        this.userRepository = userRepository;
    }

    @Test
    void 포인트_가입_정상작동() {
        User user = new User("test", "test", "tset");
        userRepository.save(user);

        pointAwardService.award(user.getId(), 5000);
        Point point = pointRepository.findByUserIdOrderByIdDescForUpdate(user.getId());

        assertNotNull(point.getId());
        assertThat(point.getUser()).isEqualTo(user);
        assertThat(point.getTotalPoint()).isEqualTo(5000);
    }

    @Test
    void 포인트_가입_아이디_없음() {
        User user = new User("test", "test", "tset");
        userRepository.save(user);

        Long id = userRepository.validateUser("test").getId();

        UsernameNotFoundException e = assertThrows(UsernameNotFoundException.class, () ->
                pointAwardService.award(id + 1L, 5000)
        );

        assertEquals("Invalid user", e.getMessage());
        assertThat(pointRepository.existsById(id)).isFalse();
    }

    @Test
    void 포인트_가입_Max_초과() {
        User user = new User("test", "test", "tset");
        userRepository.save(user);

        Long id = userRepository.validateUser("test").getId();

        assertThrows(ConstraintViolationException.class, () ->
                pointAwardService.award(id, Integer.MAX_VALUE)
        );
    }

    @Test
    void 포인트_가입_Min_초과() {
        User user = new User("test", "test", "tset");
        userRepository.save(user);

        Long id = userRepository.validateUser("test").getId();

        assertThrows(NotEnoughPointException.class, () ->
                pointAwardService.award(id, Integer.MIN_VALUE)
        );
    }
}