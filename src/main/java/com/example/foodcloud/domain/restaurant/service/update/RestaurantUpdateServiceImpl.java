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
public class RestaurantUpdateServiceImpl implements RestaurantUpdateService {
    private final RestaurantRepository restaurantRepository;

    @Override
    public boolean update(Long userId, Long restaurantId, RestaurantUpdateServiceDto restaurantUpdateServiceDto) {
        Optional<Restaurant> restaurantOptional = restaurantRepository.findByUserIdAndId(userId, restaurantId);

        if (restaurantOptional.isPresent()) {

            Restaurant restaurant = restaurantOptional.get();

            restaurant.update(restaurantUpdateServiceDto.getName(),
                    restaurantUpdateServiceDto.getLocation(),
                    restaurantUpdateServiceDto.getBusinessHours()
            );
            return true;
        }
        return false;
    }
}