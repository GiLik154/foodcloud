package com.example.foodcloud.domain.foodmenu.service.add;

import com.example.foodcloud.domain.foodmenu.service.add.dto.FoodMenuAddServiceDto;

import java.io.File;

public interface FoodMenuAddService {
    void add(Long userId, Long restaurantId, FoodMenuAddServiceDto foodMenuAddServiceDto, File file);
}
