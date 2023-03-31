package com.example.foodcloud.domain.restaurant.service.update;

import com.example.foodcloud.domain.restaurant.service.update.dto.RestaurantUpdateServiceDto;

public interface RestaurantUpdateService {
    void update(Long userId, Long restaurantId, RestaurantUpdateServiceDto restaurantUpdateServiceDto);
}