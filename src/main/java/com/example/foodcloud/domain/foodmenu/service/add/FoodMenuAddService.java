package com.example.foodcloud.domain.foodmenu.service.add;

import com.example.foodcloud.domain.foodmenu.service.add.dto.FoodMenuAddServiceDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface FoodMenuAddService {
    boolean add(Long userId, Long restaurantId, FoodMenuAddServiceDto foodMenuAddServiceDto, File file);
}
