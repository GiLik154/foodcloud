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
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class FoodMenuAddServiceImpl implements FoodMenuAddService {
    private final ImageUploadService imageUploadService;
    private final FoodMenuRepository foodMenuRepository;
    private final RestaurantRepository restaurantRepository;

    public boolean add(Long userId, Long restaurantId, FoodMenuAddServiceDto foodMenuAddServiceDto, MultipartFile file) {
        Optional<Restaurant> restaurantOptional = restaurantRepository.findByUserIdAndId(userId, restaurantId);
        if (restaurantOptional.isPresent()) {
            FoodMenu foodMenu = new FoodMenu(foodMenuAddServiceDto.getFoodMenu(),
                    foodMenuAddServiceDto.getPrice(),
                    foodMenuAddServiceDto.getFoodType(),
                    foodMenuAddServiceDto.getTemperature(),
                    foodMenuAddServiceDto.getMeatType(),
                    foodMenuAddServiceDto.getVegetables(),
                    restaurantOptional.get()
            );

            if (file != null) {
                imageUploadService.upload(restaurantOptional.get().getName(), file, foodMenu);
            }

            foodMenuRepository.save(foodMenu);

            return true;
        }
        return false;
    }
}
