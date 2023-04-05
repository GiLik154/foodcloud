package com.example.foodcloud.domain.order.join.service.update;

import com.example.foodcloud.domain.order.menu.service.update.OrderMenuListResultUpdateService;
import com.example.foodcloud.enums.OrderResult;
import com.example.foodcloud.domain.order.join.domain.OrderJoinGroup;
import com.example.foodcloud.domain.order.join.domain.OrderJoinGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderJoinGroupResultUpdateServiceImpl implements OrderJoinGroupResultUpdateService {
    private final OrderJoinGroupRepository orderJoinGroupRepository;
    private final OrderMenuListResultUpdateService orderMenuListResultUpdateService;

    @Override
    public void update(Long userId, Long orderJoinGroupId, String result) {
        Optional<OrderJoinGroup> orderJoinGroupOptional = orderJoinGroupRepository.findByUserIdAndId(userId, orderJoinGroupId);

        orderJoinGroupOptional.ifPresent(orderJoinGroup -> {
            OrderResult orderResult = OrderResult.valueOf(result);
            orderJoinGroup.updateResult(orderResult);

            orderMenuListResultUpdateService.update(orderJoinGroupId, orderResult);
        });
    }
}
