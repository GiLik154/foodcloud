package com.example.foodcloud.domain.order.join.domain;


import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.enums.OrderResult;
import com.example.foodcloud.exception.NotFoundOrderJoinGroupException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

public interface OrderJoinGroupRepository extends JpaRepository<OrderJoinGroup, Long> {
    List<OrderJoinGroup> findByUserId(Long userId);

    Optional<OrderJoinGroup> findByUserIdAndId(Long userId, Long orderJoinGroupId);

    boolean existsByUserIdAndId(Long userId, Long orderJoinGroupId);

    Optional<OrderJoinGroup> findByUserIdAndIdAndResultNot(Long userId, Long orderJoinGroupId, OrderResult orderResult);

    Optional<OrderJoinGroup> findByIdAndResultNot(Long orderJoinGroupId, OrderResult orderResult);

    default OrderJoinGroup validateOrderJoinGroupNotCancel(Long userId, Long orderJoinGroupId) {
        Optional<OrderJoinGroup> orderJoinGroupOptional = findByUserIdAndIdAndResultNot(userId, orderJoinGroupId, OrderResult.CANCELED);

        return orderJoinGroupOptional.orElseThrow(NotFoundOrderJoinGroupException::new);
    }

    default OrderJoinGroup validateOrderJoinGroupNotCancel(Long orderJoinGroupId) {
        Optional<OrderJoinGroup> orderJoinGroupOptional = findByIdAndResultNot(orderJoinGroupId, OrderResult.CANCELED);

        return orderJoinGroupOptional.orElseThrow(NotFoundOrderJoinGroupException::new);
    }
}
