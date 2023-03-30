package com.example.foodcloud.domain.restaurant.service.update;

import com.example.foodcloud.domain.restaurant.domain.Restaurant;
import com.example.foodcloud.domain.restaurant.domain.RestaurantRepository;
import com.example.foodcloud.domain.restaurant.service.update.dto.RestaurantUpdateServiceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantCountUpdateServiceImpl implements RestaurantCountUpdateService {
    private final RestaurantRepository restaurantRepository;

    @Override
    public void updateOrderCount(Long restaurantId) {
        restaurantRepository.findById(restaurantId).ifPresent(Restaurant::updateOrderCount);
    }
}