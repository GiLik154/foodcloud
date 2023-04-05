package com.example.foodcloud.domain.foodmenu.service.update;

import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenuRepository;
import com.example.foodcloud.domain.foodmenu.service.image.ImageUploadService;
import com.example.foodcloud.domain.foodmenu.service.update.dto.FoodMenuUpdateServiceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class FoodMenuUpdateServiceImpl implements FoodMenuUpdateService {
    private final ImageUploadService imageUploadService;
    private final FoodMenuRepository foodMenuRepository;

    @Override
    public void update(Long foodMenuId, FoodMenuUpdateServiceDto foodMenuUpdateServiceDto, File file) {
        Optional<FoodMenu> foodMenuOptional = foodMenuRepository.findById(foodMenuId);

        foodMenuOptional.ifPresent(foodMenu -> {
            if (file != null) {
                foodMenu.uploadImage(
                        imageUploadService.saveFileAndReturnFilePath(foodMenu.getRestaurant().getName(), file)
                );
            }

            foodMenu.update(foodMenuUpdateServiceDto.getName(),
                    foodMenuUpdateServiceDto.getPrice(),
                    foodMenuUpdateServiceDto.getTemperature(),
                    foodMenuUpdateServiceDto.getFoodTypes(),
                    foodMenuUpdateServiceDto.getMeatType(),
                    foodMenuUpdateServiceDto.getVegetables()
            );
        });
    }
}
