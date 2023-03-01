package com.example.foodcloud.domain.foodmenu.domain;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodMenuRepository extends JpaRepository<FoodMenu, Long> {
    List<FoodMenu> findByRestaurantId(Long restaurantId);
}
