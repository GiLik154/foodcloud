package com.example.foodcloud.domain.restaurant.service.add;

import com.example.foodcloud.domain.restaurant.service.add.dto.RestaurantAddServiceDto;

public interface RestaurantAddService {
    void add(Long userId, RestaurantAddServiceDto restaurantAddServiceDto);
}
