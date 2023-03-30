package com.example.foodcloud.domain.restaurant.service.update;

import com.example.foodcloud.domain.restaurant.service.update.dto.RestaurantUpdateServiceDto;

public interface RestaurantCountUpdateService {
    void updateOrderCount(Long restaurantId);
}