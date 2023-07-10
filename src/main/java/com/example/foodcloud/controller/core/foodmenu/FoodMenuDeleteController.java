package com.example.foodcloud.controller.core.foodmenu;

import com.example.foodcloud.domain.foodmenu.domain.FoodMenuRepository;
import com.example.foodcloud.domain.foodmenu.service.FoodMenuDeleter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/food-menu/delete")
public class FoodMenuDeleteController {
    private final FoodMenuDeleter foodMenuDeleter;
    private final FoodMenuRepository foodMenuRepository;

    @GetMapping("")
    public String get(@RequestParam Long foodMenuId, Model model) {
        foodMenuRepository.findById(foodMenuId).ifPresent(foodMenu ->
                model.addAttribute("foodMenu", foodMenu));

        return "thymeleaf/food-menu/delete";
    }

    @PostMapping("")
    public String post(@SessionAttribute("userId") Long userId,
                       @RequestParam Long foodMenuId,
                       String password) {
        foodMenuDeleter.delete(userId, foodMenuId, password);

        return "thymeleaf/food-menu/delete";
    }
}
