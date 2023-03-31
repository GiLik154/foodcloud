package com.example.foodcloud.domain.foodmenu.service.add;

import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenuRepository;
import com.example.foodcloud.domain.foodmenu.service.add.dto.FoodMenuAddServiceDto;
import com.example.foodcloud.domain.foodmenu.service.image.ImageUploadService;
import com.example.foodcloud.domain.restaurant.domain.Restaurant;
import com.example.foodcloud.domain.restaurant.domain.RestaurantRepository;
import com.example.foodcloud.enums.FoodType;
import com.example.foodcloud.enums.foodmenu.FoodTypes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class FoodMenuAddServiceImpl implements FoodMenuAddService {
    private final ImageUploadService imageUploadService;
    private final FoodMenuRepository foodMenuRepository;
    private final RestaurantRepository restaurantRepository;

    public boolean add(Long userId, Long restaurantId, FoodMenuAddServiceDto foodMenuAddServiceDto, File file) {
        Optional<Restaurant> restaurantOptional = restaurantRepository.findByUserIdAndId(userId, restaurantId);
        if (restaurantOptional.isPresent()) {
            FoodMenu foodMenu = new FoodMenu(foodMenuAddServiceDto.getName(),
                    foodMenuAddServiceDto.getPrice(),
                    foodMenuAddServiceDto.getTemperature(),
                    foodMenuAddServiceDto.getFoodTypes(),
                    foodMenuAddServiceDto.getMeatType(),
                    foodMenuAddServiceDto.getVegetables(),
                    restaurantOptional.get()
            );

            if (file != null) {
                foodMenu.uploadImage(
                        imageUploadService.saveFileAndReturnFilePath(restaurantOptional.get().getName(), file)
                );
            }

            foodMenuRepository.save(foodMenu);

            return true;
        }
        return false;
    }
}
