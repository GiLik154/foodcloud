package com.example.foodcloud.domain.restaurant.domain;


import com.example.foodcloud.exception.NotFoundRestaurantException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Optional<Restaurant> findByUserIdAndId(Long userId, Long restaurantId);

    List<Restaurant> findByUserId(Long userId);

    boolean existsByUserId(Long userId);

    boolean existsById(Long restaurantId);

    default Restaurant validateRestaurant(Long restaurantId) {
        Optional<Restaurant> restaurantOptional = findById(restaurantId);

        return restaurantOptional.orElseThrow(NotFoundRestaurantException::new);
    }

    default List<Restaurant> validateRestaurantByUserId(Long userId) {
        if (existsByUserId(userId)) {
            return findByUserId(userId);
        }

        throw new NotFoundRestaurantException();
    }
}
