package com.example.foodcloud.domain.order.join.domain;


import com.example.foodcloud.enums.OrderResult;
import com.example.foodcloud.exception.NotFoundOrderJoinGroupException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderJoinGroupRepository extends JpaRepository<OrderJoinGroup, Long> {
    List<OrderJoinGroup> findByUserId(Long userId);

    Optional<OrderJoinGroup> findByUserIdAndId(Long userId, Long orderJoinGroupId);

    default OrderJoinGroup findValidByUserIdAndIdAndNotCancel(Long userId, Long orderJoinGroupId) {
        Optional<OrderJoinGroup> orderJoinGroupOptional = findByUserIdAndIdAndResultNot(userId, orderJoinGroupId, OrderResult.CANCELED);

        return orderJoinGroupOptional.orElseThrow(NotFoundOrderJoinGroupException::new);
    }

    Optional<OrderJoinGroup> findByUserIdAndIdAndResultNot(Long userId, Long orderJoinGroupId, OrderResult orderResult);

    default OrderJoinGroup findValidByIdAndNotCancel(Long orderJoinGroupId) {
        Optional<OrderJoinGroup> orderJoinGroupOptional = findByIdAndResultNot(orderJoinGroupId, OrderResult.CANCELED);

        return orderJoinGroupOptional.orElseThrow(NotFoundOrderJoinGroupException::new);
    }

    Optional<OrderJoinGroup> findByIdAndResultNot(Long orderJoinGroupId, OrderResult orderResult);
}
