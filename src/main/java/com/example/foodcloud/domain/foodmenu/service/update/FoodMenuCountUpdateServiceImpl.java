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

    /**
     * 주어진 음식 메뉴의 주문 수량을 1 증가시킵니다.
     * 동시에 여러 사용자가 접근하여 데이터 불일치 문제가 발생하지 않도록,
     * 비관적 락을 사용하여 해당 음식 메뉴 레코드를 업데이트합니다.
     *
     * @param foodMenuId 증가시킬 음식 메뉴의 ID
     */
    @Override
    public void increaseOrderCount(Long foodMenuId) {
        foodMenuRepository.findByIdForUpdate(foodMenuId)
                .ifPresent(FoodMenu::updateOrderMenu);
    }
}
