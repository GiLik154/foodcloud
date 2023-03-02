package com.example.foodcloud.domain.foodmenu.service.delete;


import com.example.foodcloud.domain.foodmenu.domain.FoodMenuRepository;
import com.example.foodcloud.domain.user.service.validate.UserValidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class FoodMenuDeleteServiceImpl implements FoodMenuDeleteService {
    private final FoodMenuRepository foodMenuRepository;
    private final UserValidateService userValidateService;

    @Override
    public boolean delete(Long userId, Long foodMenuId, String password) {
        if (foodMenuRepository.existsById(foodMenuId)) {
            userValidateService.validate(userId, password);

            foodMenuRepository.deleteById(foodMenuId);

            return true;
        }
        return false;
    }
}