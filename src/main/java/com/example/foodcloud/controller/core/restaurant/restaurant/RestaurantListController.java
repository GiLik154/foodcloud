package com.example.foodcloud.controller.core.restaurant.restaurant;

import com.example.foodcloud.domain.restaurant.domain.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequiredArgsConstructor
@RequestMapping("/restaurant")
public class RestaurantListController {
    private final RestaurantRepository restaurantRepository;

    @GetMapping("/list")
    public String get(@SessionAttribute("userId") Long userId, Model model) {
            model.addAttribute("restaurantList", restaurantRepository.validateRestaurantByUserId(userId));
            return "thymeleaf/restaurant/list";
    }
}
