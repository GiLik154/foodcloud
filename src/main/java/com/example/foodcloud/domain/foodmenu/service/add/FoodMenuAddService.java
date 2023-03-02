package com.example.foodcloud.domain.foodmenu.service.add;

import com.example.foodcloud.domain.foodmenu.service.dto.FoodMenuDto;
import org.springframework.web.multipart.MultipartFile;

public interface FoodMenuAddService {
    boolean add(Long userId, Long restaurantId, FoodMenuDto foodMenuDto, MultipartFile file);
}
