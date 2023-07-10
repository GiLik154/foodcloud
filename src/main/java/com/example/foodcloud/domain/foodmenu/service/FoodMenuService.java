package com.example.foodcloud.domain.foodmenu.service;

import com.example.foodcloud.application.image.ImageUploader;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenuRepository;
import com.example.foodcloud.domain.foodmenu.service.commend.FoodMenuCreatorCommend;
import com.example.foodcloud.domain.foodmenu.service.commend.FoodMenuUpdaterCommend;
import com.example.foodcloud.domain.restaurant.domain.Restaurant;
import com.example.foodcloud.domain.restaurant.domain.RestaurantRepository;
import com.example.foodcloud.domain.user.domain.User;
import com.example.foodcloud.domain.user.domain.UserRepository;
import com.example.foodcloud.exception.NotFoundFoodMenuException;
import com.example.foodcloud.exception.NotFoundRestaurantException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;

@Service
@Transactional
@RequiredArgsConstructor
public class FoodMenuService implements FoodMenuCreator, FoodMenuUpdater, FoodMenuDeleter {
    private final ImageUploader imageUploader;
    private final FoodMenuRepository foodMenuRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    @Override
    public void create(Long userId, Long restaurantId, FoodMenuCreatorCommend commend, File file) {
        Restaurant restaurant = restaurantRepository.findByUserIdAndId(userId, restaurantId).orElseThrow(() -> new NotFoundRestaurantException("Not found restaurant"));

        FoodMenu foodMenu = new FoodMenu(commend.getName(), commend.getPrice(), commend.getTemperature(),
                commend.getFoodTypes(), commend.getMeatType(), commend.getVegetables(), restaurant);

        imageUpload(file, foodMenu, restaurant.getName());

        foodMenuRepository.save(foodMenu);
    }

    @Override
    public void update(Long foodMenuId, FoodMenuUpdaterCommend commend, File file) {
        FoodMenu foodMenu = findFoodMenu(foodMenuId);

        foodMenu.update(commend.getName(), commend.getPrice(), commend.getTemperature(),
                commend.getFoodTypes(), commend.getMeatType(), commend.getVegetables());

        imageUpload(file, foodMenu, foodMenu.getRestaurant().getName());
    }

    private void imageUpload(File file, FoodMenu foodMenu, String restaurantName) {
        if (file != null) {
            String imagePath = getImagePath(restaurantName, file);

            foodMenu.uploadImage(imagePath);
        }
    }

    private String getImagePath(String restaurantName, File file) {
        return imageUploader.savedFileAndReturnFilePath(restaurantName, file);
    }

    @Override
    public void increaseOrderCount(Long foodMenuId) {
        foodMenuRepository.findByIdForUpdate(foodMenuId).ifPresent(FoodMenu::incrementOrderCount);
    }

    @Override
    public void delete(Long userId, Long foodMenuId, String password) {
        FoodMenu foodMenu = findFoodMenu(foodMenuId);

        User user = findUser(userId);

        validPassword(user, password);

        foodMenuRepository.delete(foodMenu);
    }

    private FoodMenu findFoodMenu(Long foodMenuId){
        return foodMenuRepository.findById(foodMenuId).orElseThrow(() -> new NotFoundFoodMenuException("Not found food menu"));
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    private void validPassword(User user, String password) {
        if (!user.isValidPassword(bCryptPasswordEncoder, password))
            throw new BadCredentialsException("Invalid password");
    }
}
