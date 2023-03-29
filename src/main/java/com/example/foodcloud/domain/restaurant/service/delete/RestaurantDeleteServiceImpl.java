package com.example.foodcloud.domain.restaurant.service.delete;


import com.example.foodcloud.domain.restaurant.domain.RestaurantRepository;
import com.example.foodcloud.domain.user.service.validate.ValidateUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantDeleteServiceImpl implements RestaurantDeleteService {
    private final RestaurantRepository restaurantRepository;
    private final ValidateUserService validateUserService;

    @Override
    public boolean delete(Long userId, Long restaurantId, String password) {
        if (restaurantRepository.existsById(restaurantId)) {
            validateUserService.validate(userId, password);

            restaurantRepository.deleteById(restaurantId);

            return true;
        }
        return false;
    }
}