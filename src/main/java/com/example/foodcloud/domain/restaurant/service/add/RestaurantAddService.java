package com.example.foodcloud.domain.restaurant.service.add;

import com.example.foodcloud.domain.restaurant.service.add.dto.RestaurantAddDto;

public interface RestaurantAddService {
    void add(Long userId, RestaurantAddDto restaurantAddDto);
}
