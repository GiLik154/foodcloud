package com.example.foodcloud.domain.restaurant.domain;


import com.example.foodcloud.exception.NotFoundRestaurantException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Optional<Restaurant> findByUserIdAndId(Long userId, Long restaurantId);

    boolean existsById(Long restaurantId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT r FROM Restaurant r WHERE r.id = :id")
    Optional<Restaurant> findByIdForUpdate(@Param("id") Long restaurantId);

    default Restaurant validateById(Long restaurantId) {
        Optional<Restaurant> restaurantOptional = findById(restaurantId);

        return restaurantOptional.orElseThrow(NotFoundRestaurantException::new);
    }

    default List<Restaurant> validateRestaurantByUserId(Long userId) {
        if (existsByUserId(userId)) {
            return findByUserId(userId);
        }
        throw new NotFoundRestaurantException();
    }

    boolean existsByUserId(Long userId);

    List<Restaurant> findByUserId(Long userId);
}
