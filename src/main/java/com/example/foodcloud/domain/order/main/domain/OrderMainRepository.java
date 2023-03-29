package com.example.foodcloud.domain.order.main.domain;


import com.example.foodcloud.enums.OrderResult;
import com.example.foodcloud.exception.NotFoundOrderMainException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderMainRepository extends JpaRepository<OrderMain, Long> {
    String CANCEL_CODE = OrderResult.CANCELED.getResult();

    List<OrderMain> findByUserId(Long userId);

    Optional<OrderMain> findByUserIdAndId(Long userId, Long orderMainId);

    boolean existsByUserIdAndId(Long userId, Long orderMainId);

    Optional<OrderMain> findByUserIdAndIdAndResultNot(Long userId, Long orderMainId, String result);

    Optional<OrderMain> findByIdAndResultNot(Long orderMainId, String result);

    default OrderMain validateOrderMainNotCancel(Long userId, Long orderMainId) {
        Optional<OrderMain> orderMainOptional = findByUserIdAndIdAndResultNot(userId, orderMainId, CANCEL_CODE);

        return orderMainOptional.orElseThrow(NotFoundOrderMainException::new);
    }

    default OrderMain validateOrderMainNotCancel(Long orderMainId) {
        Optional<OrderMain> orderMainOptional = findByIdAndResultNot(orderMainId, CANCEL_CODE);

        return orderMainOptional.orElseThrow(NotFoundOrderMainException::new);
    }
}
