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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PointAwardServiceImplTest {
    private final PointAwardService pointAwardService;
    private final PointRepository pointRepository;
    private final UserJoinService userJoinService;
    private final UserRepository userRepository;

    @Autowired
    PointAwardServiceImplTest(PointAwardService pointAwardService, PointRepository pointRepository, UserJoinService userJoinService, UserRepository userRepository) {
        this.pointAwardService = pointAwardService;
        this.pointRepository = pointRepository;
        this.userJoinService = userJoinService;
        this.userRepository = userRepository;
    }

    @Test
    void 포인트_가입_정상작동() {
        JoinServiceDto joinServiceDto = new JoinServiceDto("test", "test", "test");
        userJoinService.join(joinServiceDto);

        User user = userRepository.findUser("test");
        Long id = user.getId();

        pointAwardService.award(id, 5000);
        Point point = pointRepository.findByUserIdOrderByIdDesc(id);

        assertThat(point.getUser()).isEqualTo(user);
        assertThat(point.getTotalPoint()).isEqualTo(5000);
    }

    @Test
    void 포인트_가입_아이디_없음() {
        JoinServiceDto joinServiceDto = new JoinServiceDto("test", "test", "test");
        userJoinService.join(joinServiceDto);

        Long id = userRepository.findUser("test").getId();

        UsernameNotFoundException e = assertThrows(UsernameNotFoundException.class, () ->
                pointAwardService.award(id + 1L, 5000)
        );

        assertEquals("Invalid name", e.getMessage());
        assertThat(pointRepository.existsById(id)).isFalse();
    }

    @Test
    void 포인트_가입_Max_초과() {
        JoinServiceDto joinServiceDto = new JoinServiceDto("test", "test", "test");
        userJoinService.join(joinServiceDto);

        Long id = userRepository.findUser("test").getId();

        assertThrows(ConstraintViolationException.class, () ->
                pointAwardService.award(id, Integer.MAX_VALUE)
        );
    }

    @Test
    void 포인트_가입_Min_초과() {
        JoinServiceDto joinServiceDto = new JoinServiceDto("test", "test", "test");
        userJoinService.join(joinServiceDto);

        Long id = userRepository.findUser("test").getId();

        assertThrows(OutOfBoundsPointException.class, () ->
                pointAwardService.award(id, Integer.MIN_VALUE)
        );
    }
}