package com.example.foodcloud.domain.foodmenu.domain;


import com.example.foodcloud.exception.NotFoundFoodMenuException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FoodMenuRepository extends JpaRepository<FoodMenu, Long> {
    List<FoodMenu> findByRestaurantId(Long foodMenuId);

    boolean existsById(Long foodMenuId);

    List<FoodMenu> findByVegetablesAndRestaurant_Location(String vegetables ,String location);
    List<FoodMenu> findByMeatTypeAndRestaurant_Location(String meatType ,String location);
    List<FoodMenu> findByTemperatureAndRestaurant_Location(String temperature ,String location);

    Optional<FoodMenu> findById(Long foodMenuId);

    default FoodMenu validateFoodMenu(Long foodMenuId) {
        Optional<FoodMenu> foodMenuOptional = findById(foodMenuId);

        return foodMenuOptional.orElseThrow(NotFoundFoodMenuException::new);
    }
}
