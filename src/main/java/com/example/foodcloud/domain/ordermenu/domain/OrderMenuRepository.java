package com.example.foodcloud.domain.ordermenu.domain;


import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.enums.OrderResult;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderMenuRepository extends JpaRepository<OrderMenu, Long> {
    List<OrderMenu> findByGroupBuyListId(Long listId);

    List<OrderMenu> findByUserId(Long userId);

    @Query("SELECT o FROM OrderMenu o JOIN FETCH o.user u JOIN FETCH o.payment WHERE u.id = :userId")
    List<OrderMenu> findByUserIdFetchJoin(@Param("userId") Long userId);

    @Query("SELECT o FROM OrderMenu o JOIN FETCH o.user u LEFT JOIN FETCH o.payment WHERE o.id = :orderMenuId")
    Optional<OrderMenu> findByIdFetchJoin(@Param("orderMenuId") Long orderMenuId);

    List<OrderMenu> findByFoodMenuIdAndResult(Long foodMenuId, OrderResult result);

    @Query("SELECT o FROM OrderMenu o LEFT JOIN FETCH o.user LEFT JOIN FETCH o.payment WHERE o.id = :orderMenuId AND o.result != :result")
    Optional<OrderMenu> findByIdAndResultNotFetchJoin(@Param("orderMenuId") Long orderMenuId, @Param("result") OrderResult result);

    Optional<OrderMenu> findByUserIdAndId(Long userId, Long orderMenuId);

    @Query("SELECT f.foodMenu FROM OrderMenu f JOIN f.user u WHERE u.id = :userId GROUP BY f.foodMenu")
    List<FoodMenu> findByFoodMenuByUserId(@Param("userId") Long userId, Pageable pageable);
}