package com.example.foodcloud.domain.order.main.domain;


import com.example.foodcloud.exception.NotFoundOrderMainException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderMainRepository extends JpaRepository<OrderMain, Long> {
    List<OrderMain> findByUserId(Long userId);
    Optional<OrderMain> findByUserIdAndId(Long userId, Long orderMainId);

    boolean existsByUserIdAndId(Long userId, Long orderMainId);

    default OrderMain validateOrderMain(Long userId, Long orderMainId) {
        Optional<OrderMain> orderMainOptional = findByUserIdAndId(userId, orderMainId);

        return orderMainOptional.orElseThrow(NotFoundOrderMainException::new);
    }

    default OrderMain validateOrderMain(Long orderMainId) {
        Optional<OrderMain> orderMainOptional = findById(orderMainId);

        return orderMainOptional.orElseThrow(NotFoundOrderMainException::new);
    }
}
