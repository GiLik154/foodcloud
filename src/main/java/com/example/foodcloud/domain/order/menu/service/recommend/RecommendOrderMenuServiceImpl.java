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

        List<FoodMenu> foodMenus = foodMenuRepository.findByFoodTypeAndRestaurantLocation(foodMenu.getFoodType(), location);

        Collections.shuffle(foodMenus);

        return foodMenus.subList(0, validateLimit(foodMenus.size()));
    }

    private int getRandomInt(int limit) {
        Random random = new SecureRandom();

        return random.nextInt(limit);
    }

    private FoodMenu getRandomFoodMenu(Long userId) {
        Pageable pageable = PageRequest.of(0, 5, JpaSort.unsafe("COUNT(f)").descending());

        List<FoodMenu> foodMenus = orderMenuRepository.countByFoodMenuByUserId(userId, pageable);

        return foodMenus.get(getRandomInt(validateLimit(foodMenus.size())));
    }

    private int validateLimit(int limit) {
        if (limit > 5) {
            limit = 5;
        } else if (limit == 0) {
            throw new UsernameNotFoundException("Invalid user");
        }
        return limit;
    }
}
