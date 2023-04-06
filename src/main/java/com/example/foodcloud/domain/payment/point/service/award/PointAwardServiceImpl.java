package com.example.foodcloud.domain.payment.point.service.award;

import com.example.foodcloud.domain.payment.point.domain.Point;
import com.example.foodcloud.domain.payment.point.domain.PointRepository;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import com.example.foodcloud.enums.PaymentCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * point를 새로 만드는 서비스
 */
@Service
@Transactional
@RequiredArgsConstructor
public class PointAwardServiceImpl implements PointAwardService {
    private final PointRepository pointRepository;
    private final UserRepository userRepository;

    @Override
    public void award(Long userId) {
        User user = userRepository.getValidById(userId);

        Point awardPoint = new Point(user, PaymentCode.POINT);

        pointRepository.save(awardPoint);
    }
}
