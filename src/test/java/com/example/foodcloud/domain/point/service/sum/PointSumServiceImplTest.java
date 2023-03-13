package com.example.foodcloud.domain.point.service.sum;

import com.example.foodcloud.domain.point.domain.Point;
import com.example.foodcloud.domain.point.domain.PointRepository;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import com.example.foodcloud.exception.OutOfBoundsPointException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PointSumServiceImplTest {
    private final PointSumService pointSumService;
    private final PointRepository pointRepository;
    private final UserRepository userRepository;

    @Autowired
    PointSumServiceImplTest(PointSumService pointSumService, PointRepository pointRepository, UserRepository userRepository) {
        this.pointSumService = pointSumService;
        this.pointRepository = pointRepository;
        this.userRepository = userRepository;
    }

    @Test
    void 포인트_추가_정상작동() {
        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();

        Point point = new Point(5000, 5000, user);
        pointRepository.save(point);

        boolean isSum = pointSumService.sum(userId, 5000);
        Point pointSum = pointRepository.findByUserIdOrderByIdDescForUpdate(userId);

        assertTrue(isSum);
        assertThat(pointSum.getUser()).isEqualTo(user);
        assertThat(pointSum.getCalculationPoints()).isEqualTo(5000);
        assertThat(pointSum.getTotalPoint()).isEqualTo(10000);
    }

    @Test
    void 포인트_차감_정상작동() {
        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();

        Point point = new Point(5000, 5000, user);
        pointRepository.save(point);

        boolean isSum = pointSumService.sum(userId, -1000);
        Point pointSum = pointRepository.findByUserIdOrderByIdDescForUpdate(userId);

        assertTrue(isSum);
        assertThat(pointSum.getUser()).isEqualTo(user);
        assertThat(pointSum.getCalculationPoints()).isEqualTo(-1000);
        assertThat(pointSum.getTotalPoint()).isEqualTo(4000);
    }

    @Test
    void 포인트_차감_아이디_없음() {
        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();

        Point point = new Point(5000, 5000, user);
        pointRepository.save(point);

        boolean isSum = pointSumService.sum(userId + 1L, -1000);
        Point pointSum = pointRepository.findByUserIdOrderByIdDescForUpdate(userId);

        assertFalse(isSum);
        assertThat(pointSum.getUser()).isEqualTo(user);
        assertThat(pointSum.getTotalPoint()).isEqualTo(5000);
    }

    @Test
    void 포인트_추가_오버플로() {
        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();

        Point point = new Point(5000, 5000, user);
        pointRepository.save(point);

        assertThrows(ArithmeticException.class, () -> pointSumService.sum(userId, Integer.MAX_VALUE));
    }

    @Test
    void 포인트_차감_잔고없음() {
        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();

        Point point = new Point(5000, 5000, user);
        pointRepository.save(point);

        assertThrows(OutOfBoundsPointException.class, () -> pointSumService.sum(userId, Integer.MIN_VALUE));
    }

    @Test
    void 포인트_추가_버전_정상작동() {
        User user = new User("test", "test", "test");
        userRepository.save(user);
        Long userId = user.getId();

        Point point = new Point(5000, 5000, user);
        pointRepository.save(point);

        boolean isSum = false;

        for (int i = 0; i < 5; i++) {
            isSum = pointSumService.sum(userId, 5000);
        }
        Point pointSum = pointRepository.findByUserIdOrderByIdDescForUpdate(userId);

        assertTrue(isSum);
        assertEquals(5, pointSum.getVersion());
        assertEquals(user, pointSum.getUser());
        assertEquals(5000, pointSum.getCalculationPoints());
        assertEquals(30000, pointSum.getTotalPoint());
    }

}