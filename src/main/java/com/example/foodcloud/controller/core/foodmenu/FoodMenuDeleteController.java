package com.example.foodcloud.controller.core.foodmenu;

import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenuRepository;
import com.example.foodcloud.domain.foodmenu.service.delete.FoodMenuDeleteService;
import com.example.foodcloud.exception.NotFoundFoodMenuException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/food-menu/delete")
public class FoodMenuDeleteController {
    private final FoodMenuDeleteService foodMenuDeleteService;
    private final FoodMenuRepository foodMenuRepository;

    @GetMapping("")
    public String get(@RequestParam Long foodMenuId, Model model) {
        FoodMenu foodMenu = foodMenuRepository.findById(foodMenuId)
                .orElseThrow(NotFoundFoodMenuException::new);

        model.addAttribute("bankAccountInfo", foodMenu);
        return "thymeleaf/food-menu/delete";
    }

    @PostMapping("")
    public String post(@SessionAttribute("userId") Long userId, Long foodMenuId, String password, Model model) {
        model.addAttribute("isDelete", foodMenuDeleteService.delete(userId, foodMenuId, password));

        return "thymeleaf/food-menu/delete";
    }
}
