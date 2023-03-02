package com.example.foodcloud.domain.foodmenu.service.delete;

public interface FoodMenuDeleteService {
    boolean delete(Long userId, Long foodMenuId, String password);
}
