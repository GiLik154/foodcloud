package com.example.foodcloud.domain.order.menu.service.recommend;

import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenuRepository;
import com.example.foodcloud.domain.order.menu.domain.OrderMenuRepository;
import com.example.foodcloud.domain.restaurant.domain.Restaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.ArrayList;
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
        List<FoodMenu> recommendList = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            FoodMenu foodMenu = getRandomFoodMenu(userId);
            Restaurant restaurant = foodMenu.getRestaurant();
            List<FoodMenu> foodMenuList = foodMenuRepository.findByVegetablesAndRestaurant_Location(foodMenu.getVegetables(), restaurant.getLocation());
            recommendList.add(
                    foodMenuList.get(
                            getRandomListSize(foodMenuList.size())
                    ));
        } //todo

        return recommendList;
    }

    private int getRandomInt() {
        Random random = new SecureRandom();

        return random.nextInt(5);
    }

    private FoodMenu getRandomFoodMenu(Long userId) {
        List<FoodMenu> list = orderMenuRepository.countByFoodMenuByUserId(userId);

        return list.get(getRandomInt());
    }

    private int getRandomListSize(int listSize) {
        Random random = new SecureRandom();

        return random.nextInt(listSize);
    }
}
