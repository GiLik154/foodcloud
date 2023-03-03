package com.example.foodcloud.domain.restaurant.domain;


import com.example.foodcloud.exception.NotFoundRestaurantException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Optional<Restaurant> findByUserIdAndId(Long userId, Long restaurantId);

    List<Restaurant> findByUserId(Long userID);

    boolean existsById(Long restaurantId);

    default Restaurant validateRestaurant(Long userId, Long restaurantId) {
        Optional<Restaurant> restaurantOptional = findByUserIdAndId(userId, restaurantId);

        return restaurantOptional.orElseThrow(NotFoundRestaurantException::new);
    }
}
