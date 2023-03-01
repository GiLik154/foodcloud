package com.example.foodcloud.domain.point.service.sum;

import com.example.foodcloud.domain.point.domain.Point;
import com.example.foodcloud.domain.point.domain.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PointSumServiceImpl implements PointSumService {
    private final PointRepository pointRepository;

    public boolean sum(Long userId, int point) {
        if (pointRepository.existsByUserId(userId)) {
            Point sumPoint = pointRepository.findByUserIdOrderByIdDesc(userId);

            sumPoint.updatePoint(point);

            return true;
        }
        return false;
    }
}
