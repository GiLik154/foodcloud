package com.example.foodcloud.domain.restaurant.service.update;

import com.example.foodcloud.domain.restaurant.service.update.dto.RestaurantUpdateServiceDto;

public interface RestaurantUpdateService {
    /**
     * 유저의 아이디와 식당의 아이디로 식당이 존재하는지 확인한다.
     * 존재하면 restaurantUpdateServiceDto를 통해서 정보를 수장한다.
     *
     * @param userId                     유저의 아이디
     * @param restaurantId               식당의 아이디
     * @param restaurantUpdateServiceDto 수정할 정보가 담긴 DTO
     */
    void update(Long userId, Long restaurantId, RestaurantUpdateServiceDto restaurantUpdateServiceDto);
}