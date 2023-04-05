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

    /**
     * point를 새로 만드는
     * userId를 통해 user를 찾고
     * (validate에서 UsernameNotFoundException익셉션 발생할 수 있음)
     * Point의 생성자를 통해 생성
     */
    @Override
    public void award(Long userId) {
        User user = userRepository.validate(userId);

        Point awardPoint = new Point(user, PaymentCode.POINT);

        pointRepository.save(awardPoint);
    }
}
