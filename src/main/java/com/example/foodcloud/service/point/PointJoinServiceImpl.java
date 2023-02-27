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
public class PointJoinServiceImpl implements PointJoinService {
    private final PointRepository pointRepository;
    private final UserRepository userRepository;

    @Override
    public void join(Long id, int point) {
        Point pointJoin = new Point(Math.addExact(0, point), userRepository.findUser(id));
        pointRepository.save(pointJoin);
    }
}
