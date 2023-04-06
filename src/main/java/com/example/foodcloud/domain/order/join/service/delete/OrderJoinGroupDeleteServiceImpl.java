package com.example.foodcloud.domain.order.join.service.delete;

import com.example.foodcloud.domain.order.join.domain.OrderJoinGroup;
import com.example.foodcloud.domain.order.join.domain.OrderJoinGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderJoinGroupDeleteServiceImpl implements OrderJoinGroupDeleteService {
    private final OrderJoinGroupRepository orderJoinGroupRepository;

    @Override
    public void delete(Long userId, Long orderJoinGroupId) {
        Optional<OrderJoinGroup> orderJoinGroupOptional = orderJoinGroupRepository.findByUserIdAndId(userId, orderJoinGroupId);

        orderJoinGroupOptional.ifPresent(orderJoinGroupRepository::delete);
    }
}
