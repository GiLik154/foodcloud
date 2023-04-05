package com.example.foodcloud.domain.foodmenu.service.update;

import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class FoodMenuCountUpdateServiceImpl implements FoodMenuCountUpdateService {
    private final FoodMenuRepository foodMenuRepository;

    @Override
    public void increaseOrderCount(Long foodMenuId) {
        foodMenuRepository.findByIdForUpdate(foodMenuId)
                .ifPresent(FoodMenu::updateOrderMenu);
    }
}
