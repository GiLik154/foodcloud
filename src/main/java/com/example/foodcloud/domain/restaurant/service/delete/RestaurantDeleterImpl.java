package com.example.foodcloud.domain.restaurant.service.delete;


import com.example.foodcloud.domain.restaurant.domain.RestaurantRepository;
import com.example.foodcloud.domain.user.service.validate.UserValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantDeleterImpl implements RestaurantDeleter {
    private final RestaurantRepository restaurantRepository;
    private final UserValidation userValidation;

    @Override
    public void delete(Long userId, Long restaurantId, String password) {
        userValidation.validate(userId, password);

        restaurantRepository.findById(restaurantId).
                ifPresent(restaurantRepository::delete);
    }
}