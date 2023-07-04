package com.example.foodcloud.controller.core.restaurant.restaurant;

import com.example.foodcloud.domain.restaurant.domain.RestaurantRepository;
import com.example.foodcloud.domain.restaurant.service.delete.RestaurantDeleter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/restaurant/delete")
public class RestaurantDeleteController {
    private final RestaurantDeleter restaurantDeleter;
    private final RestaurantRepository restaurantRepository;

    @GetMapping("")
    public String get(@RequestParam Long restaurantId, Model model) {
        model.addAttribute("restaurantInfo", restaurantRepository.getValidById(restaurantId));
        return "thymeleaf/restaurant/delete";
    }

    @PostMapping("")
    public String post(@SessionAttribute("userId") Long userId,
                       @RequestParam Long restaurantId,
                       String password) {

        restaurantDeleter.delete(userId, restaurantId, password);

        return "redirect:/restaurant/list";
    }

}
