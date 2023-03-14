package com.example.foodcloud.domain.restaurant.service.update;

import com.example.foodcloud.domain.restaurant.service.update.dto.RestaurantUpdateServiceDto;

public interface RestaurantUpdateService {
    boolean update(Long userId, Long restaurantId, RestaurantUpdateServiceDto restaurantUpdateServiceDto);
}