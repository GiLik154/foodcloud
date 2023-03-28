package com.example.foodcloud.domain.order.menu.menu.domain;


import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.enums.OrderResult;
import com.example.foodcloud.exception.NotFoundOrderMenuException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderMenuRepository extends JpaRepository<OrderMenu, Long> {
    OrderResult CANCELED = OrderResult.CANCELED;

    List<OrderMenu> findByOrderMainId(Long orderMainId);
    List<OrderMenu> findByUserId(Long userId);

    Optional<OrderMenu> findByUserIdAndId(Long userId, Long orderMenuId);

    Optional<OrderMenu> findByIdAndResultNot(Long orderMenuId, OrderResult result);

    boolean existsByUserIdAndId(Long userId, Long orderMenuId);

    @Query("SELECT f.foodMenu FROM OrderMenu f JOIN f.user u WHERE u.id = :userId GROUP BY f.foodMenu")
    List<FoodMenu> countByFoodMenuByUserId(@Param("userId") Long userId, Pageable pageable);

    List<OrderMenu> findByFoodMenuIdAndResult(Long foodMenuId, OrderResult result);

    default OrderMenu findByResultNotCancel(Long orderMenuId) {
        Optional<OrderMenu> orderMenu = findByIdAndResultNot(orderMenuId, CANCELED);

        return orderMenu.orElseThrow(NotFoundOrderMenuException::new);
    }

    default OrderMenu validateForUserIdAndId(Long userId, Long orderMenuId) {
        Optional<OrderMenu> orderMenu = findByUserIdAndId(userId, orderMenuId);

        return orderMenu.orElseThrow(NotFoundOrderMenuException::new);
    }
}
