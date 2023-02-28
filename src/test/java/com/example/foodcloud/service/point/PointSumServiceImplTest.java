package com.example.foodcloud.service.point;

import com.example.foodcloud.entity.Point;
import com.example.foodcloud.entity.PointRepository;
import com.example.foodcloud.entity.User;
import com.example.foodcloud.entity.UserRepository;
import com.example.foodcloud.exception.OutOfBoundsPointException;
import com.example.foodcloud.service.user.UserJoinService;
import com.example.foodcloud.service.user.dto.JoinServiceDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PointSumServiceImplTest {
    private final PointSumService pointSumService;
    private final PointAwardService pointAwardService;
    private final PointRepository pointRepository;
    private final UserJoinService userJoinService;
    private final UserRepository userRepository;

    @Autowired
    PointSumServiceImplTest(PointSumService pointSumService, PointAwardService pointAwardService, PointRepository pointRepository, UserJoinService userJoinService, UserRepository userRepository) {
        this.pointSumService = pointSumService;
        this.pointAwardService = pointAwardService;
        this.pointRepository = pointRepository;
        this.userJoinService = userJoinService;
        this.userRepository = userRepository;
    }

    @Test
    void 포인트_추가_정상작동() {
        JoinServiceDto joinServiceDto = new JoinServiceDto("test", "test", "test");
        userJoinService.join(joinServiceDto);

        User user = userRepository.findUser("test");
        Long id = user.getId();

        pointAwardService.award(id, 5000);

        pointSumService.sum(id, 5000);
        Point point = pointRepository.findByUserIdOrderByIdDesc(id);

        assertThat(point.getUser()).isEqualTo(user);
        assertThat(point.getCalculationPoints()).isEqualTo(5000);
        assertThat(point.getTotalPoint()).isEqualTo(10000);
    }

    @Test
    void 포인트_차감_정상작동() {
        JoinServiceDto joinServiceDto = new JoinServiceDto("test", "test", "test");
        userJoinService.join(joinServiceDto);

        User user = userRepository.findUser("test");
        Long id = user.getId();

        pointAwardService.award(id, 5000);

        pointSumService.sum(id, -1000);
        Point point = pointRepository.findByUserIdOrderByIdDesc(id);

        assertThat(point.getUser()).isEqualTo(user);
        assertThat(point.getCalculationPoints()).isEqualTo(-1000);
        assertThat(point.getTotalPoint()).isEqualTo(4000);
    }

    @Test
    void 포인트_추가_오버플로() {
        JoinServiceDto joinServiceDto = new JoinServiceDto("test", "test", "test");
        userJoinService.join(joinServiceDto);

        User user = userRepository.findUser("test");
        Long id = user.getId();

        pointAwardService.award(id, 5000);

        assertThrows(ArithmeticException.class, () -> pointSumService.sum(id, Integer.MAX_VALUE));
    }

    @Test
    void 포인트_차감_잔고없음() {
        JoinServiceDto joinServiceDto = new JoinServiceDto("test", "test", "test");
        userJoinService.join(joinServiceDto);

        User user = userRepository.findUser("test");
        Long id = user.getId();

        pointAwardService.award(id, 5000);

        assertThrows(OutOfBoundsPointException.class, () -> pointSumService.sum(id, Integer.MIN_VALUE));
    }
}