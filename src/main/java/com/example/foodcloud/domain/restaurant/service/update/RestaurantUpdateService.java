package com.example.foodcloud.domain.restaurant.service.update;

import com.example.foodcloud.domain.restaurant.service.update.dto.RestaurantUpdateDto;

public interface RestaurantUpdateService {
    boolean update(Long userId, Long restaurantId, RestaurantUpdateDto restaurantUpdateDto);
}