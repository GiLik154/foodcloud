package com.example.foodcloud.controller.core.restaurant;

import com.example.foodcloud.domain.restaurant.domain.RestaurantRepository;
import com.example.foodcloud.domain.restaurant.service.delete.RestaurantDeleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/restaurant")
public class RestaurantDeleteController {
    private final RestaurantDeleteService restaurantDeleteService;
    private final RestaurantRepository restaurantRepository;

    @GetMapping("/delete")
    public String get(Long restaurantId, Model model) {
        model.addAttribute("restaurantInfo", restaurantRepository.validateRestaurant(restaurantId));
        return "thymeleaf/restaurant/delete";
    }

    @PostMapping("/delete")
    public String post(@SessionAttribute("userId") Long userId,
                       Long restaurantId,
                       String password,
                       Model model) {
        model.addAttribute("isDelete", restaurantDeleteService.delete(userId, restaurantId, password));

        return "thymeleaf/restaurant/delete";
    }

}
