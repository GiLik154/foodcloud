package com.example.foodcloud.service.point;

import com.example.foodcloud.entity.Point;
import com.example.foodcloud.entity.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PointSumServiceImpl implements PointSumService {
    private final PointRepository pointRepository;

    public void sum(Long userId, int point) {
        Point sumPoint = pointRepository.findByUserIdOrderByIdDesc(userId);

        sumPoint.validatePoint(point);

        pointRepository.save(sumPoint);
    }
}
