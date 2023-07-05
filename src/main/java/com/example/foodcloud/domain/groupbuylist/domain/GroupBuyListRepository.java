package com.example.foodcloud.domain.groupbuylist.domain;


import com.example.foodcloud.enums.OrderResult;
import com.example.foodcloud.exception.NotFoundOrderJoinGroupException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroupBuyListRepository extends JpaRepository<GroupBuyList, Long> {
    List<GroupBuyList> findByUserId(Long userId);

    Optional<GroupBuyList> findByUserIdAndId(Long userId, Long orderJoinGroupId);

    default GroupBuyList findValidByUserIdAndIdAndNotCancel(Long userId, Long orderJoinGroupId) {
        Optional<GroupBuyList> orderJoinGroupOptional = findByUserIdAndIdAndResultNot(userId, orderJoinGroupId, OrderResult.CANCELED);

        return orderJoinGroupOptional.orElseThrow(NotFoundOrderJoinGroupException::new);
    }

    Optional<GroupBuyList> findByUserIdAndIdAndResultNot(Long userId, Long orderJoinGroupId, OrderResult orderResult);

    default GroupBuyList findValidByIdAndNotCancel(Long orderJoinGroupId) {
        Optional<GroupBuyList> orderJoinGroupOptional = findByIdAndResultNot(orderJoinGroupId, OrderResult.CANCELED);

        return orderJoinGroupOptional.orElseThrow(NotFoundOrderJoinGroupException::new);
    }

    Optional<GroupBuyList> findByIdAndResultNot(Long orderJoinGroupId, OrderResult orderResult);
}
