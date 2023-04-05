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

    /**
     * 사용자의 ID와 음식 메뉴의 ID로 해당 음식이 존재하는지 확인
     * 존재한다면 사용자의 ID와 패스워드를 일치하는지 검증함
     * (일치하지 않으면 BadCredentialsException익셉션 발생)
     * 일치하면 음식 메뉴를 삭제함
     *
     * @param userId     사용자의 ID
     * @param foodMenuId 음식 메뉴의 ID
     * @param password   사용자의 패스워드
     */
    @Override
    public void delete(Long userId, Long foodMenuId, String password) {
        Optional<FoodMenu> foodMenuOptional = foodMenuRepository.findById(foodMenuId);

        foodMenuOptional.ifPresent(foodMenu -> {
            validateUserPasswordService.validate(userId, password);
            foodMenuRepository.delete(foodMenu);
        });
    }
}