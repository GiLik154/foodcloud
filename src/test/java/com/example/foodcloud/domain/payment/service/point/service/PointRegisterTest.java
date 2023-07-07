package com.example.foodcloud.domain.payment.service.point.service;

import com.example.foodcloud.UserFixture;
import com.example.foodcloud.domain.payment.domain.Point;
import com.example.foodcloud.domain.payment.domain.PointRepository;
import com.example.foodcloud.domain.payment.service.point.PointRegister;
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
class PointRegisterTest {
    private final PointRegister pointRegister;
    private final PointRepository pointRepository;
    private final UserRepository userRepository;

    @Autowired
    PointRegisterTest(PointRegister pointRegister, PointRepository pointRepository, UserRepository userRepository) {
        this.pointRegister = pointRegister;
        this.pointRepository = pointRepository;
        this.userRepository = userRepository;
    }

    @Test
    void 포인트_가입_정상작동() {
        User user = userRepository.save(UserFixture.fixture().build());

        pointRegister.register(user.getId());
        Point point = pointRepository.findByUserId(user.getId()).get();

        assertNotNull(point.getId());
        assertThat(point.getUser()).isEqualTo(user);
    }

    @Test
    void 유저의_고유변호가_다르면_익셉션_발생() {
        User user = userRepository.save(UserFixture.fixture().build());

        Long userId = user.getId();

        assertThrows(UsernameNotFoundException.class, () -> pointRegister.register(userId + 1L));

        assertThat(pointRepository.existsById(userId)).isFalse();
    }
}