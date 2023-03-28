package com.example.foodcloud.domain.order.menu.menu.service.recommend;

import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;

import java.util.List;

public interface RecommendOrderMenuService {
    List<FoodMenu> recommend(Long userId, String location);
}
