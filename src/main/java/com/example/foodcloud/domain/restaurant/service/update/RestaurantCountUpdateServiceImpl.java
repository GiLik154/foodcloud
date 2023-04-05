package com.example.foodcloud.domain.restaurant.service.update;

import com.example.foodcloud.domain.restaurant.domain.Restaurant;
import com.example.foodcloud.domain.restaurant.domain.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantCountUpdateServiceImpl implements RestaurantCountUpdateService {
    private final RestaurantRepository restaurantRepository;
    /**
     * 주어진 식당의 주문 수량을 1 증가시킵니다.
     * 동시에 여러 사용자가 접근하여 데이터 불일치 문제가 발생하지 않도록,
     * 비관적 락을 사용하여 해당 식당의 레코드를 업데이트합니다.
     *
     * @param restaurantId 증가시킬 식당의 ID
     */
    @Override
    public void increaseOrderCount(Long restaurantId) {
        restaurantRepository.findByIdForUpdate(restaurantId)
                .ifPresent(Restaurant::updateOrderCount);
    }
}