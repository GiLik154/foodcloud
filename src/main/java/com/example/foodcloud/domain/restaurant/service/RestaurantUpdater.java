package com.example.foodcloud.domain.restaurant.service;

import com.example.foodcloud.domain.restaurant.service.commend.RestaurantUpdaterCommend;

public interface RestaurantUpdater {
    /**
     * 유저의 아이디와 식당의 아이디로 식당이 존재하는지 확인한다.
     * 존재하면 {@link RestaurantUpdaterCommend}를 통해서 정보를 수장한다.
     *
     * @param userId                   유저의 아이디
     * @param restaurantId             식당의 아이디
     * @param restaurantUpdaterCommend 식당의 수정할 정보
     */
    void update(Long userId, Long restaurantId, RestaurantUpdaterCommend restaurantUpdaterCommend);
}