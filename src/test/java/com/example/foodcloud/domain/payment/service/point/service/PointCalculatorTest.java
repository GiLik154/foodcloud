package com.example.foodcloud.domain.payment.service.point.service;

import com.example.foodcloud.UserFixtures;
import com.example.foodcloud.domain.payment.domain.Point;
import com.example.foodcloud.domain.payment.domain.PointRepository;
import com.example.foodcloud.domain.payment.service.point.PointCalculator;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import com.example.foodcloud.exception.NotEnoughPointException;
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
class PointCalculatorTest {
    private final PointCalculator pointCalculator;
    private final PointRepository pointRepository;
    private final UserRepository userRepository;

    @Autowired
    PointCalculatorTest(PointCalculator pointCalculator, PointRepository pointRepository, UserRepository userRepository) {
        this.pointCalculator = pointCalculator;
        this.pointRepository = pointRepository;
        this.userRepository = userRepository;
    }

    @Test
    void 포인트_추가_정상작동() {
        User user = UserFixtures.fixtures().build();
        userRepository.save(user);
        Long userId = user.getId();

        Point point = new Point(user);
        point.update(5000);
        pointRepository.save(point);

        pointCalculator.sum(userId, 5000);
        Point pointSum = pointRepository.findByUserId(userId).get();

        assertThat(pointSum.getUser()).isEqualTo(user);
        assertThat(pointSum.getRecentPoint()).isEqualTo(5000);
        assertThat(pointSum.getTotalPoint()).isEqualTo(10000);
    }

    @Test
    void 포인트_차감_정상작동() {
        User user = UserFixtures.fixtures().build();
        userRepository.save(user);
        Long userId = user.getId();

        Point point = new Point(user);
        point.update(5000);
        pointRepository.save(point);

        pointCalculator.sum(userId, -1000);
        Point pointSum = pointRepository.findByUserId(userId).get();

        assertThat(pointSum.getUser()).isEqualTo(user);
        assertThat(pointSum.getRecentPoint()).isEqualTo(-1000);
        assertThat(pointSum.getTotalPoint()).isEqualTo(4000);
    }

    @Test
    void 유저의_아이디가_다르면_익셉션_발생() {
        User user = UserFixtures.fixtures().build();
        userRepository.save(user);
        Long userId = user.getId();

        Point point = new Point(user);
        point.update(5000);
        pointRepository.save(point);

        pointCalculator.sum(userId + 1L, -1000);
        Point pointSum = pointRepository.findByUserId(userId).get();

        assertThat(pointSum.getUser()).isEqualTo(user);
        assertThat(pointSum.getTotalPoint()).isEqualTo(5000);
    }

    @Test
    void 오버플로시_익셉션_발생() {
        User user = UserFixtures.fixtures().build();
        userRepository.save(user);
        Long userId = user.getId();

        Point point = new Point(user);
        point.update(5000);
        pointRepository.save(point);

        assertThrows(ArithmeticException.class, () -> pointCalculator.sum(userId, Integer.MAX_VALUE));
    }

    @Test
    void 잔고가_모자라면_익셉션_발생() {
        User user = UserFixtures.fixtures().build();
        userRepository.save(user);
        Long userId = user.getId();

        Point point = new Point(user);
        point.update(5000);
        pointRepository.save(point);

        assertThrows(NotEnoughPointException.class, () -> pointCalculator.sum(userId, Integer.MIN_VALUE));
    }

    @Test
    void 포인트_추가_버전_정상작동() {
        User user = UserFixtures.fixtures().build();
        userRepository.save(user);
        Long userId = user.getId();

        Point point = new Point(user);
        point.update(5000);
        pointRepository.save(point);


        for (int i = 0; i < 5; i++) {
            pointCalculator.sum(userId, 5000);
        }
        Point pointSum = pointRepository.findByUserId(userId).get();

        assertEquals(5, pointSum.getVersion());
        assertEquals(user, pointSum.getUser());
        assertEquals(5000, pointSum.getRecentPoint());
        assertEquals(30000, pointSum.getTotalPoint());
    }

}