package com.example.foodcloud.domain.foodmenu.service.update;

import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenuRepository;
import com.example.foodcloud.domain.foodmenu.service.image.ImageUploadService;
import com.example.foodcloud.domain.foodmenu.service.dto.FoodMenuDto;
import com.example.foodcloud.domain.order.menu.domain.OrderMenu;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.LockModeType;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class FoodMenuUpdateServiceImpl implements FoodMenuUpdateService {
    private final ImageUploadService imageUploadService;
    private final FoodMenuRepository foodMenuRepository;

    @Override
    public boolean update(Long foodMenuId, Long restaurantId, FoodMenuDto foodMenuDto, MultipartFile file) {
        Optional<FoodMenu> foodMenuOptional = foodMenuRepository.findById(foodMenuId);
        if (foodMenuOptional.isPresent()) {
            FoodMenu foodMenu = foodMenuOptional.get();

            foodMenu.update(foodMenuDto.getFoodMenu(),
                    foodMenuDto.getPrice(),
                    foodMenuDto.getFoodType(),
                    foodMenuDto.getTemperature(),
                    foodMenuDto.getMeatType(),
                    foodMenuDto.getVegetables()
            );

            if (file != null) {
                imageUploadService.upload(restaurantId, file, foodMenu);
            }

            return true;
        }
        return false;
    }

    @Override
    public void updateFoodMenuOrderCount(Long foodMenuId, OrderMenu orderMenu) {
        FoodMenu foodMenu = foodMenuRepository.findByIdForUpdate(foodMenuId);
        foodMenu.updateOrderMenu(orderMenu);
    }


}
