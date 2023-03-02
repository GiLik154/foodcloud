package com.example.foodcloud.domain.foodmenu.service.update;

import com.example.foodcloud.domain.foodmenu.service.dto.FoodMenuDto;
import org.springframework.web.multipart.MultipartFile;

public interface FoodMenuUpdateService {
    boolean update(Long foodMenuId, Long restaurantId, FoodMenuDto foodMenuDto, MultipartFile file);
}
