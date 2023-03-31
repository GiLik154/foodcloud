package com.example.foodcloud.domain.restaurant.service.delete;

public interface RestaurantDeleteService {
    void delete(Long userId, Long restaurantId, String password);
}
