package com.example.foodcloud.domain.order.menu.service.recommend;

import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenuRepository;
import com.example.foodcloud.domain.order.menu.domain.OrderMenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
@Transactional
@RequiredArgsConstructor
public class RecommendOrderMenuServiceImpl implements RecommendOrderMenuService {
    private final OrderMenuRepository orderMenuRepository;
    private final FoodMenuRepository foodMenuRepository;

    @Override
    public List<FoodMenu> recommend(Long userId, String location) {
        FoodMenu foodMenu = getRandomFoodMenu(userId);

        List<FoodMenu> foodMenus = foodMenuRepository.findByFoodTypesAndRestaurantLocation(foodMenu.getFoodTypes(), location);

        Collections.shuffle(foodMenus);

        return foodMenus.subList(0, limitForceFive(foodMenus.size()));
    }

    /**
     * FoodMenu를
     * 랜덤으로 하나 반환하는 메소드
     * (db에서 처리 가능하나, DB에 무리가 많이 가기 때문에
     * Java에서 처리함)
     *
     * @param userId 유저의 ID
     */
    private FoodMenu getRandomFoodMenu(Long userId) {
        Pageable pageable = PageRequest.of(0, 5, JpaSort.unsafe("COUNT(f)").descending());

        List<FoodMenu> foodMenus = orderMenuRepository.findByFoodMenuByUserId(userId, pageable);

        return foodMenus.get(getRandomInt(foodMenus.size()));
    }

    /**
     * 5개 중에서 랜덤으로
     * FoodMenu를 찾기 위해
     * 0~4 사이에서 숫자를 하나 뽑는 메소드
     *
     * @param count List<FoodMenu>의 사이즈를 limitForceFive의 메소드를 통해 5 이하로 줄임
     */
    private int getRandomInt(int count) {
        if (count == 0) {
            throw new UsernameNotFoundException("Invalid user");
        }

        Random random = new SecureRandom();

        return random.nextInt(count);
    }

    /**
     * count의 숫자가 5를 넘어가면
     * 5로 바꿔주는 메소드
     *
     * @param count 움삭메뉴를 랜덤으루 출력할 갯수 (최대 5개)
     */
    private int limitForceFive(int count) {
        if (count > 5) {
            count = 5;
        }

        return count;
    }
}
