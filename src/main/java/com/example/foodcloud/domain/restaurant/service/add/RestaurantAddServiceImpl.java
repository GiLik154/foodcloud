package com.example.foodcloud.domain.restaurant.service.add;

import com.example.foodcloud.domain.restaurant.domain.Restaurant;
import com.example.foodcloud.domain.restaurant.domain.RestaurantRepository;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import com.example.foodcloud.domain.restaurant.service.add.dto.RestaurantAddServiceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantAddServiceImpl implements RestaurantAddService {
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    /**
     * 식당 추가 로직
     * 유저가 존재하는지 검증함 (익셉션 발생할 수 있음)
     * 이후 AddServiceDto를 통해서 값을 받아와서
     * 식당을 추가함.
     */
    @Override
    public void add(Long userId, RestaurantAddServiceDto restaurantAddServiceDto) {
        User user = userRepository.validateUser(userId);

        Restaurant restaurant = new Restaurant(restaurantAddServiceDto.getName(),
                restaurantAddServiceDto.getLocation(),
                restaurantAddServiceDto.getBusinessHours(),
                user);

        restaurantRepository.save(restaurant);
    }
}