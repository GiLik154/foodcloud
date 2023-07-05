package com.example.foodcloud.domain.restaurant.service;


public interface RestaurantCountUpdater {
    /**
     * 식당의 ID로 식당이 존재하는지 확인한다.
     * 존재하면 해당 식당의 주문 수량을 1 증가시킨다
     * 동시에 여러 사용자가 접근하여 데이터 불일치 문제가 발생하지 않도록,
     * 비관적 락을 사용하여 해당 식당의 레코드를 업데이트한다.
     *
     * @param restaurantId 증가시킬 식당의 ID
     */
    void increaseOrderCount(Long restaurantId);
}