package com.example.foodcloud.domain.payment.point.service.sum;

import com.example.foodcloud.domain.payment.point.domain.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PointSumServiceImpl implements PointSumService {
    private final PointRepository pointRepository;

    public void sum(Long userId, int price) {
        pointRepository.findByUserIdOrderByIdDescForUpdate(userId).
                ifPresent(point -> point.updatePoint(price));
    }
}
