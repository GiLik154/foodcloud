package com.example.foodcloud.domain.foodmenu.service.add;

import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenuRepository;
import com.example.foodcloud.domain.foodmenu.service.add.dto.FoodMenuAddServiceDto;
import com.example.foodcloud.domain.foodmenu.service.image.ImageUploadService;
import com.example.foodcloud.domain.restaurant.domain.Restaurant;
import com.example.foodcloud.domain.restaurant.domain.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class FoodMenuAddServiceImpl implements FoodMenuAddService {
    private final ImageUploadService imageUploadService;
    private final FoodMenuRepository foodMenuRepository;
    private final RestaurantRepository restaurantRepository;

    public void add(Long userId, Long restaurantId, FoodMenuAddServiceDto foodMenuAddServiceDto, File file) {
        Optional<Restaurant> restaurantOptional = restaurantRepository.findByUserIdAndId(userId, restaurantId);

        restaurantOptional.ifPresent(restaurant -> {
            FoodMenu foodMenu = new FoodMenu(foodMenuAddServiceDto.getName(),
                    foodMenuAddServiceDto.getPrice(),
                    foodMenuAddServiceDto.getTemperature(),
                    foodMenuAddServiceDto.getFoodTypes(),
                    foodMenuAddServiceDto.getMeatType(),
                    foodMenuAddServiceDto.getVegetables(),
                    restaurant
            );

            if (file != null) {
                foodMenu.uploadImage(
                        imageUploadService.saveFileAndReturnFilePath(restaurant.getName(), file)
                );
            }

            foodMenuRepository.save(foodMenu);
        });
    }
}
