package com.example.foodcloud.domain.order.menu.domain;


import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderMenuRepository extends JpaRepository<OrderMenu, Long> {
    List<OrderMenu> findByOrderMainId(Long orderMainId);

    Optional<OrderMenu> findByUserIdAndId(Long userId, Long orderMenuId);

    boolean existsByUserIdAndId(Long userId, Long orderMenuId);
    @Query("SELECT f.foodMenu FROM OrderMenu f JOIN f.user u WHERE u.id = :userId GROUP BY f.foodMenu")
    List<FoodMenu> countByFoodMenuByUserId(@Param("userId") Long userId, Pageable pageable);
}
