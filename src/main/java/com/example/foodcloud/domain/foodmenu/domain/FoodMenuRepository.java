package com.example.foodcloud.domain.foodmenu.domain;


import com.example.foodcloud.domain.bank.domain.BankAccount;
import com.example.foodcloud.exception.NotFoundBankAccountException;
import com.example.foodcloud.exception.NotFoundFoodMenuException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FoodMenuRepository extends JpaRepository<FoodMenu, Long> {
    List<FoodMenu> findByRestaurantId(Long restaurantId);

    boolean existsById(Long foodMenuId);

    Optional<FoodMenu> findByUserIdAndId(Long userId, Long foodMenuId);


    default FoodMenu validateFoodMenu(Long userId, Long foodMenuId) {
        Optional<FoodMenu> foodMenuOptional = findByUserIdAndId(userId, foodMenuId);

        return foodMenuOptional.orElseThrow(() ->
                new NotFoundFoodMenuException()
        );
    }
}
