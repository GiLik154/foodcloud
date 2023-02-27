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
public class PointSumServiceImpl implements PointSumService{
    private final PointRepository pointRepository;
    private final UserRepository userRepository;
    public void sum(Long id, int price){
        Point point = pointRepository.getById(id);

        point.sumPoint(price);

    }
}
