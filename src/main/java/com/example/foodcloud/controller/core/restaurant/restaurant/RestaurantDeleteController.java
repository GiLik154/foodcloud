package com.example.foodcloud.controller.core.restaurant.restaurant;

import com.example.foodcloud.domain.restaurant.domain.Restaurant;
import com.example.foodcloud.domain.restaurant.domain.RestaurantRepository;
import com.example.foodcloud.domain.restaurant.service.RestaurantDeleter;
import com.example.foodcloud.exception.NotFoundRestaurantException;
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
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new NotFoundRestaurantException("Not found Restaurant"));
        model.addAttribute("restaurantInfo", restaurant);
        return "thymeleaf/restaurant/delete";
    }

    @PostMapping("")
    public String post(@SessionAttribute("userId") Long userId,
                       @RequestParam Long restaurantId,
                       String password) {

        restaurantDeleter.delete(userId, password, restaurantId);

        return "redirect:/restaurant/list";
    }

}
