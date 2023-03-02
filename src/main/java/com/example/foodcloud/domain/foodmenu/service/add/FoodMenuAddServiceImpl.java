package com.example.foodcloud.domain.foodmenu.service.add;

import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenuRepository;
import com.example.foodcloud.domain.foodmenu.service.image.ImageUploadService;
import com.example.foodcloud.domain.foodmenu.service.dto.FoodMenuDto;
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

    public boolean add(Long userId, Long restaurantId, FoodMenuDto foodMenuDto, MultipartFile file) {
        Optional<Restaurant> restaurantOptional = restaurantRepository.findByUserIdAndId(userId, restaurantId);
        if (restaurantOptional.isPresent()) {
            FoodMenu foodMenu = new FoodMenu(foodMenuDto.getFoodMenu(),
                    foodMenuDto.getPrice(),
                    foodMenuDto.getFoodType(),
                    foodMenuDto.getTemperature(),
                    foodMenuDto.getMeatType(),
                    foodMenuDto.getVegetables(),
                    restaurantOptional.get()
            );

            if (file != null) {
                imageUploadService.upload(restaurantId, file, foodMenu);
            }

            foodMenuRepository.save(foodMenu);

            return true;
        }
        return false;
    }
}
