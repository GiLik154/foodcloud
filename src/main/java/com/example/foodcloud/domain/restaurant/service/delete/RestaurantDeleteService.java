package com.example.foodcloud.domain.restaurant.service.delete;

public interface RestaurantDeleteService {
    boolean delete(Long userId, Long restaurantId, String password);
}
