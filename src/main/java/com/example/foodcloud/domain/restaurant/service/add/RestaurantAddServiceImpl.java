package com.example.foodcloud.domain.restaurant.service.add;

import com.example.foodcloud.domain.restaurant.domain.Restaurant;
import com.example.foodcloud.domain.restaurant.domain.RestaurantRepository;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import com.example.foodcloud.domain.restaurant.service.add.dto.RestaurantAddDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantAddServiceImpl implements RestaurantAddService {
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    @Override
    public void add(Long userId, RestaurantAddDto restaurantAddDto) {
        User user = userRepository.validateUser(userId);

        Restaurant restaurant = new Restaurant(restaurantAddDto.getName(),
                restaurantAddDto.getLocation(),
                restaurantAddDto.getBusinessHours(),
                user);

        restaurantRepository.save(restaurant);
    }
}