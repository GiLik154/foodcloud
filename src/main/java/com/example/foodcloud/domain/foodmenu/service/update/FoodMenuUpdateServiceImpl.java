package com.example.foodcloud.domain.foodmenu.service.update;

import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenuRepository;
import com.example.foodcloud.domain.foodmenu.service.image.ImageUploadService;
import com.example.foodcloud.domain.foodmenu.service.update.dto.FoodMenuUpdateServiceDto;
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
public class FoodMenuUpdateServiceImpl implements FoodMenuUpdateService {
    private final ImageUploadService imageUploadService;
    private final FoodMenuRepository foodMenuRepository;
    private final RestaurantRepository restaurantRepository;

    @Override
    public boolean update(Long foodMenuId, Long restaurantId, FoodMenuUpdateServiceDto foodMenuUpdateServiceDto, MultipartFile file) {
        Optional<FoodMenu> foodMenuOptional = foodMenuRepository.findById(foodMenuId);
        Restaurant restaurant = restaurantRepository.validateRestaurant(restaurantId);
        if (foodMenuOptional.isPresent()) {
            FoodMenu foodMenu = foodMenuOptional.get();

            if (file != null) {
                imageUploadService.upload(restaurant.getName(), file, foodMenu);
            }

            foodMenu.update(foodMenuUpdateServiceDto.getFoodMenu(),
                    foodMenuUpdateServiceDto.getPrice(),
                    foodMenuUpdateServiceDto.getFoodType(),
                    foodMenuUpdateServiceDto.getTemperature(),
                    foodMenuUpdateServiceDto.getMeatType(),
                    foodMenuUpdateServiceDto.getVegetables()
            );


            return true;
        }
        return false;
    }

    @Override
    public void updateFoodMenuOrderCount(Long foodMenuId) {
        FoodMenu foodMenu = foodMenuRepository.findByIdForUpdate(foodMenuId);
        foodMenu.updateOrderMenu();
    }


}
