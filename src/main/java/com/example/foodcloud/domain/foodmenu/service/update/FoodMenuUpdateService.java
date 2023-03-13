package com.example.foodcloud.domain.foodmenu.service.update;

import com.example.foodcloud.domain.foodmenu.service.update.dto.FoodMenuUpdateServiceDto;
import com.example.foodcloud.domain.order.menu.domain.OrderMenu;
import org.springframework.web.multipart.MultipartFile;

public interface FoodMenuUpdateService {
    boolean update(Long foodMenuId, Long restaurantId, FoodMenuUpdateServiceDto foodMenuUpdateServiceDto, MultipartFile file);
    void updateFoodMenuOrderCount(Long foodMenuId, OrderMenu orderMenu);
}
