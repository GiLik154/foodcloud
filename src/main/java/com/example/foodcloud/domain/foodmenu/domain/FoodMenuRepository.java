package com.example.foodcloud.domain.foodmenu.domain;


import com.example.foodcloud.exception.NotFoundFoodMenuException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

public interface FoodMenuRepository extends JpaRepository<FoodMenu, Long> {
    List<FoodMenu> findByRestaurantId(Long foodMenuId);

    boolean existsById(Long foodMenuId);

    List<FoodMenu> findByFoodTypeAndRestaurantLocation(String vegetables , String location);

    Optional<FoodMenu> findById(Long foodMenuId);
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT f FROM FoodMenu f WHERE f.id = :id")
    FoodMenu findByIdForUpdate(@Param("id") Long foodMenuId);

    default FoodMenu validateFoodMenu(Long foodMenuId) {
        Optional<FoodMenu> foodMenuOptional = findById(foodMenuId);

        return foodMenuOptional.orElseThrow(NotFoundFoodMenuException::new);
    }
}
