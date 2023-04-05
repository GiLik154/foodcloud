package com.example.foodcloud.controller.core.restaurant.restaurant;

import com.example.foodcloud.domain.restaurant.domain.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/restaurant/list")
public class RestaurantListController {
    private final RestaurantRepository restaurantRepository;

    @GetMapping("")
    public String get(Model model) {
        model.addAttribute("restaurantList", restaurantRepository.findAll());
        return "thymeleaf/restaurant/list";
    }
}
