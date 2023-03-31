package com.example.foodcloud.domain.payment.point.service.award;

import com.example.foodcloud.domain.payment.point.domain.Point;
import com.example.foodcloud.domain.payment.point.domain.PointRepository;
import com.example.foodcloud.domain.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PointAwardServiceImpl implements PointAwardService {
    private final PointRepository pointRepository;
    private final UserRepository userRepository;

    @Override
    public void award(Long userId) {
        Point awardPoint = new Point(userRepository.validateUser(userId));

        pointRepository.save(awardPoint);
    }
}
