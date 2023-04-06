package com.example.foodcloud.domain.foodmenu.service.delete;


import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenuRepository;
import com.example.foodcloud.domain.user.service.validate.ValidateUserPasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class FoodMenuDeleteServiceImpl implements FoodMenuDeleteService {
    private final FoodMenuRepository foodMenuRepository;
    private final ValidateUserPasswordService validateUserPasswordService;

    @Override
    public void delete(Long userId, Long foodMenuId, String password) {
        Optional<FoodMenu> foodMenuOptional = foodMenuRepository.findById(foodMenuId);

        foodMenuOptional.ifPresent(foodMenu -> {
            validateUserPasswordService.validate(userId, password);
            foodMenuRepository.delete(foodMenu);
        });
    }
}