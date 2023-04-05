package com.example.foodcloud.controller.core.foodmenu;

import com.example.foodcloud.domain.foodmenu.domain.FoodMenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * 음식 리스트를 보여주는 컨트롤
 */
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/food-menu/list")
public class FoodMenuListController {
    private final FoodMenuRepository foodMenuRepository;

    /**
     * RequestParam 통해 restaurantId 받아옴.
     * restaurantId를 통해 restaurantId를 가지고 있는
     * 모든 foodMenu를 출력해줌
     */
    @GetMapping("")
    public String get(@RequestParam Long restaurantId, Model model) {
        model.addAttribute("foodMenuList", foodMenuRepository.findByRestaurantId(restaurantId));
        return "thymeleaf/food-menu/list";
    }
}
