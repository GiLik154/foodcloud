package com.example.foodcloud.domain.ordermenu.domain;


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

    List<OrderMenu> findByGroupBuyListId(Long listId);

    List<OrderMenu> findByUserId(Long userId);

    List<OrderMenu> findByFoodMenuIdAndResult(Long foodMenuId, OrderResult result);

    default OrderMenu getValidByIdAndNotCanceled(Long orderMenuId) {
        Optional<OrderMenu> orderMenu = findByIdAndResultNot(orderMenuId, OrderResult.CANCELED);

        return orderMenu.orElseThrow(NotFoundOrderMenuException::new);
    }

    Optional<OrderMenu> findByIdAndResultNot(Long orderMenuId, OrderResult result);

    default OrderMenu getValidByUserIdAndId(Long userId, Long orderMenuId) {
        Optional<OrderMenu> orderMenu = findByUserIdAndId(userId, orderMenuId);

        return orderMenu.orElseThrow(NotFoundOrderMenuException::new);
    }

    Optional<OrderMenu> findByUserIdAndId(Long userId, Long orderMenuId);

    @Query("SELECT f.foodMenu FROM OrderMenu f JOIN f.user u WHERE u.id = :userId GROUP BY f.foodMenu")
    List<FoodMenu> findByFoodMenuByUserId(@Param("userId") Long userId, Pageable pageable);
}
