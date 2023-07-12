package com.example.foodcloud.domain.foodmenu.service;

import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
@RequiredArgsConstructor
public class FoodMenuCountIncreaseImpl implements FoodMenuCountIncrease {
    private final FoodMenuRepository foodMenuRepository;

    @Override
    public void increase(Long foodMenuId) {
        foodMenuRepository.findByIdForUpdate(foodMenuId).ifPresent(FoodMenu::incrementOrderCount);
    }
}