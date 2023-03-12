package com.example.foodcloud.domain.foodmenu.service.update;

import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.domain.foodmenu.service.dto.FoodMenuDto;
import com.example.foodcloud.domain.order.menu.domain.OrderMenu;
import org.springframework.web.multipart.MultipartFile;

public interface FoodMenuUpdateService {
    boolean update(Long foodMenuId, Long restaurantId, FoodMenuDto foodMenuDto, MultipartFile file);
    void updateFoodMenuOrderCount(Long foodMenuId, OrderMenu orderMenu);
}
