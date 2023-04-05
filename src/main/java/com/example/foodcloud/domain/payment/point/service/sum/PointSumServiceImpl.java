package com.example.foodcloud.domain.payment.point.service.sum;

import com.example.foodcloud.domain.payment.point.domain.Point;
import com.example.foodcloud.domain.payment.point.domain.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * point의 잔액을 수정하는 서비스
 */
@Service
@Transactional
@RequiredArgsConstructor
public class PointSumServiceImpl implements PointSumService {
    private final PointRepository pointRepository;

    /**
     * 포인트의 잔액을 수정하는 메소드
     * findByUserIdOrderByIdDescForUpdate는 낙관적 Lock을 사용함.
     * updatePoin에 point를 넘겨줘서 잔액을 수정함.
     * @param point 수정되야 할 가격
     */
    public boolean sum(Long userId, int point) {
        if (pointRepository.existsByUserId(userId)) {
            Point sumPoint = pointRepository.findByUserIdOrderByIdDescForUpdate(userId);

            sumPoint.updatePoint(point);

            return true;
        }
        return false;
    }
}
