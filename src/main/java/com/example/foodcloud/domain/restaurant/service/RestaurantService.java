package com.example.foodcloud.domain.restaurant.service;

import com.example.foodcloud.domain.restaurant.domain.Restaurant;
import com.example.foodcloud.domain.restaurant.domain.RestaurantRepository;
import com.example.foodcloud.domain.restaurant.service.commend.RestaurantRegisterCommend;
import com.example.foodcloud.domain.restaurant.service.commend.RestaurantUpdaterCommend;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantService implements RestaurantRegister, RestaurantDeleter, RestaurantCountUpdater, RestaurantUpdater {
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    @Override
    public void add(Long userId, RestaurantRegisterCommend commend) {
        User user = userFind(userId);

        Restaurant restaurant = new Restaurant(commend.getName(), commend.getLocation(),
                commend.getBusinessHours(), user);

        restaurantRepository.save(restaurant);
    }

    @Override
    public void delete(Long userId, String password, Long restaurantId) {
        User user = userFind(userId);

        validPassword(user, password);

        restaurantRepository.findById(restaurantId).
                ifPresent(restaurantRepository::delete);
    }

    private User userFind(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    private void validPassword(User user, String password) {
        if (!user.isValidPassword(bCryptPasswordEncoder, password))
            throw new BadCredentialsException("Invalid password");
    }

    @Override
    public void increaseOrderCount(Long restaurantId) {
        restaurantRepository.findByIdForUpdate(restaurantId)
                .ifPresent(Restaurant::incrementOrderCount);
    }

    @Override
    public void update(Long userId, Long restaurantId, RestaurantUpdaterCommend restaurantUpdaterCommend) {
        Optional<Restaurant> optional = restaurantRepository.findByUserIdAndId(userId, restaurantId);

        optional.ifPresent(restaurant ->
                restaurant.update(restaurantUpdaterCommend.getName(),
                        restaurantUpdaterCommend.getLocation(),
                        restaurantUpdaterCommend.getBusinessHours()
                ));
    }
}
