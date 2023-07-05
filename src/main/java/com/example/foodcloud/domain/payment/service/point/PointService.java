package com.example.foodcloud.domain.payment.service.point;

import com.example.foodcloud.domain.payment.domain.Point;
import com.example.foodcloud.domain.payment.domain.PointRepository;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PointService implements PointRegister, PointCalculator {
    private final PointRepository pointRepository;
    private final UserRepository userRepository;

    @Override
    public void register(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Point awardPoint = new Point(user);

        pointRepository.save(awardPoint);
    }

    @Override
    public void sum(Long userId, int price) {
        pointRepository.findByUserIdOrderByIdDescForUpdate(userId).
                ifPresent(point -> point.update(price));
    }
}