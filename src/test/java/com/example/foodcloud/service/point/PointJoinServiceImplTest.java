package com.example.foodcloud.service.point;

import com.example.foodcloud.entity.PointRepository;
import com.example.foodcloud.entity.UserRepository;
import com.example.foodcloud.service.user.UserJoinService;
import com.example.foodcloud.service.user.dto.JoinServiceDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PointJoinServiceImplTest {
    private final PointJoinService pointJoinService;
    private final PointRepository pointRepository;
    private final UserJoinService userJoinService;
    private final UserRepository userRepository;

    @Autowired
    PointJoinServiceImplTest(PointJoinService pointJoinService, PointRepository pointRepository, UserJoinService userJoinService, UserRepository userRepository) {
        this.pointJoinService = pointJoinService;
        this.pointRepository = pointRepository;
        this.userJoinService = userJoinService;
        this.userRepository = userRepository;
    }

    @Test
    void 포인트_가입_정상작동() {
        JoinServiceDto joinServiceDto = new JoinServiceDto("test", "test", "test");
        userJoinService.isJoin(joinServiceDto);

        Long id = userRepository.findUser("test").getId();

        pointJoinService.join(id, 5000);

        assertThat(pointRepository.findById(id).get().getId()).isEqualTo(id);
        assertThat(pointRepository.findById(id).get().getTotalPoint()).isEqualTo(5000);
    }

    @Test
    void 포인트_가입_아이디_없음() {
        JoinServiceDto joinServiceDto = new JoinServiceDto("test", "test", "test");
        userJoinService.isJoin(joinServiceDto);

        Long id = userRepository.findUser("test").getId();

        UsernameNotFoundException e = assertThrows(UsernameNotFoundException.class, () ->
                pointJoinService.join(id + 1L, 5000)
        );

        assertEquals("Invalid name", e.getMessage());
        assertThat(pointRepository.existsById(id)).isFalse();
    }

    @Test
    void 포인트_가입_오버플로() {
        JoinServiceDto joinServiceDto = new JoinServiceDto("test", "test", "test");
        userJoinService.isJoin(joinServiceDto);

        Long id = userRepository.findUser("test").getId();

        UsernameNotFoundException e = assertThrows(UsernameNotFoundException.class, () ->
                pointJoinService.join(id, Integer.MAX_VALUE)
        );


        assertEquals("Invalid name", e.getMessage());
        assertThat(pointRepository.existsById(id)).isFalse();
    }
}