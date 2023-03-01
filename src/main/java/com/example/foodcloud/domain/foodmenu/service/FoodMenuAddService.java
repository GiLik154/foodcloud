package com.example.foodcloud.domain.foodmenu.service;

import com.example.foodcloud.domain.foodmenu.service.dto.FoodMenuAddDto;
import org.springframework.web.multipart.MultipartFile;

public interface FoodMenuAddService {
    boolean add(Long userId, Long restaurantId, FoodMenuAddDto foodMenuAddDto, MultipartFile file);
}
