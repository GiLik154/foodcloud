package com.example.foodcloud.domain.foodmenu.service.update;

import com.example.foodcloud.domain.foodmenu.service.update.dto.FoodMenuUpdateServiceDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface FoodMenuUpdateService {
    boolean update(Long foodMenuId, Long restaurantId, FoodMenuUpdateServiceDto foodMenuUpdateServiceDto, File file);
    void updateFoodMenuOrderCount(Long foodMenuId);
}

