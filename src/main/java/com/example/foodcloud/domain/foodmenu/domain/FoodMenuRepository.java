package com.example.foodcloud.domain.foodmenu.domain;


import com.example.foodcloud.exception.NotFoundFoodMenuException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FoodMenuRepository extends JpaRepository<FoodMenu, Long> {
    List<FoodMenu> findByRestaurantId(Long foodMenuId);

    boolean existsById(Long foodMenuId);

    Optional<FoodMenu> findById(Long foodMenuId);

    default FoodMenu validateFoodMenu(Long foodMenuId) {
        Optional<FoodMenu> foodMenuOptional = findById(foodMenuId);

        return foodMenuOptional.orElseThrow(NotFoundFoodMenuException::new);
    }
}
