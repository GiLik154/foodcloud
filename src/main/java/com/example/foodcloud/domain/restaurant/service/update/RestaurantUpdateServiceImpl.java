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

    /**
     * 식당을 수정하는 메소드
     * 식당을 등록한 유저의 고유번호와 식당의 고유번호를 통해
     * 식당을 가지고온 후 UpateServiceDto를 통해서 수장함.
     */
    @Override
    public void update(Long userId, Long restaurantId, RestaurantUpdateServiceDto restaurantUpdateServiceDto) {
        Optional<Restaurant> restaurantOptional = restaurantRepository.findByUserIdAndId(userId, restaurantId);

        restaurantOptional.ifPresent(restaurant ->
                restaurant.update(restaurantUpdateServiceDto.getName(),
                        restaurantUpdateServiceDto.getLocation(),
                        restaurantUpdateServiceDto.getBusinessHours()
                ));
    }
}