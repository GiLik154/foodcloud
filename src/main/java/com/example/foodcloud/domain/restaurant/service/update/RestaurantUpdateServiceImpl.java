package com.example.foodcloud.domain.restaurant.service.update;

import com.example.foodcloud.domain.restaurant.domain.Restaurant;
import com.example.foodcloud.domain.restaurant.domain.RestaurantRepository;
import com.example.foodcloud.domain.restaurant.service.update.dto.RestaurantUpdateDto;
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
    public boolean update(Long userId, Long restaurantId, RestaurantUpdateDto restaurantUpdateDto) {
        Optional<Restaurant> restaurantOptional = restaurantRepository.findByUserIdAndId(userId, restaurantId);

        if (restaurantOptional.isPresent()) {

            Restaurant restaurant = restaurantOptional.get();

            restaurant.update(restaurantUpdateDto.getName(),
                    restaurantUpdateDto.getLocation(),
                    restaurantUpdateDto.getBusinessHours()
            );
            return true;
        }
        return false;
    }
}