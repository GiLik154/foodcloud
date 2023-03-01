package com.example.foodcloud.domain.restaurant.service.delete;


import com.example.foodcloud.domain.restaurant.domain.RestaurantRepository;
import com.example.foodcloud.domain.user.service.validate.UserValidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantDeleteServiceImpl implements RestaurantDeleteService {
    private final RestaurantRepository restaurantRepository;
    private final UserValidateService userValidateService;

    @Override
    public boolean delete(Long userId, Long restaurantId, String password) {
        if (restaurantRepository.existsByUserIdAndId(userId, restaurantId)) {
            userValidateService.validate(userId, password);

            restaurantRepository.deleteById(restaurantId);

            return true;
        }
        return false;
    }
}