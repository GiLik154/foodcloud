package com.example.foodcloud.domain.restaurant.service.delete;


import com.example.foodcloud.domain.restaurant.domain.RestaurantRepository;
import com.example.foodcloud.domain.user.service.validate.ValidateUserPasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantDeleteServiceImpl implements RestaurantDeleteService {
    private final RestaurantRepository restaurantRepository;
    private final ValidateUserPasswordService validateUserPasswordService;

    @Override
    public void delete(Long userId, Long restaurantId, String password) {
        validateUserPasswordService.validate(userId, password);

        restaurantRepository.findById(restaurantId).
                ifPresent(restaurantRepository::delete);
    }
}