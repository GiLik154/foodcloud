package com.example.foodcloud.domain.foodmenu.service.update;

import com.example.foodcloud.domain.foodmenu.service.update.dto.FoodMenuUpdateServiceDto;

import java.io.File;

public interface FoodMenuCountUpdateService {
    void updateOrderCount(Long foodMenuId);
}

