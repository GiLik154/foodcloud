package com.example.foodcloud.domain.order.menu.service.recommend;

import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface RecommendOrderMenuService {
    /**
     * 유저의 ID를 통해 foodMenu를 랜덤으로 가지고 온다.
     * 존재 할 경우 같은 지역에서 주문한 음식 메뉴를 가지고 온다.
     * 그 후 5개를 랜덤으로 반환해준다. (Collections.shuffle 이용)
     *
     * @param userId   유저의 ID
     * @param location 주문한 장소
     * @throws UsernameNotFoundException 유저의 ID를 통해 음식 메뉴를
     *                                   찾을 때 존재하지 않으면 발생
     */
    List<FoodMenu> recommend(Long userId, String location);
}