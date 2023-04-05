package com.example.foodcloud.domain.order.join.service.delete;

import com.example.foodcloud.domain.order.join.domain.OrderJoinGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderJoinGroupDeleteServiceImpl implements OrderJoinGroupDeleteService {
    private final OrderJoinGroupRepository orderJoinGroupRepository;

    @Override
    public boolean delete(Long userId, Long orderJoinGroupId) {
        if (orderJoinGroupRepository.existsByUserIdAndId(userId, orderJoinGroupId)) {

            orderJoinGroupRepository.deleteById(orderJoinGroupId);

            return true;
        }
        return false;
    }
}
