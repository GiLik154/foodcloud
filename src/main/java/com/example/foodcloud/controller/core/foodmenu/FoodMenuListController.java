package com.example.foodcloud.controller.core.foodmenu;

import com.example.foodcloud.domain.foodmenu.domain.FoodMenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/food-menu/list")
public class FoodMenuListController {
    private final FoodMenuRepository foodMenuRepository;
    @GetMapping("")
    public String get(Long restaurantId, Model model) {
        model.addAttribute("foodMenuList", foodMenuRepository.findByRestaurantId(restaurantId));

        return "thymeleaf/food-menu/list";
    }
}
