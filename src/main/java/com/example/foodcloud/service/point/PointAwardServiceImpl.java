package com.example.foodcloud.service.point;

import com.example.foodcloud.entity.Point;
import com.example.foodcloud.entity.PointRepository;
import com.example.foodcloud.entity.UserRepository;
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
    public void award(Long userId, int point) {
        Point awardPoint = new Point(userRepository.findUser(userId));

        awardPoint.validatePoint(point);

        pointRepository.save(awardPoint);
    }
}
