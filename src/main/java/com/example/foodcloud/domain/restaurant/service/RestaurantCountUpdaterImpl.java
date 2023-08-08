package com.example.foodcloud.domain.restaurant.service;


import com.example.foodcloud.domain.restaurant.domain.Restaurant;
import com.example.foodcloud.domain.restaurant.domain.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantCountUpdaterImpl implements RestaurantCountUpdater {
    private final RestaurantRepository restaurantRepository;

    @Override
    public void increase(Long restaurantId) {
        restaurantRepository.findByIdForUpdate(restaurantId)
                .ifPresent(Restaurant::incrementOrderCount);
    }
}