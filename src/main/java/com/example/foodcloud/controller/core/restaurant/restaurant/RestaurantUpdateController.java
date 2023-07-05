package com.example.foodcloud.controller.core.restaurant.restaurant;

import com.example.foodcloud.controller.core.restaurant.restaurant.dto.RestaurantUpdateControllerDto;
import com.example.foodcloud.domain.restaurant.domain.Restaurant;
import com.example.foodcloud.domain.restaurant.domain.RestaurantRepository;
import com.example.foodcloud.domain.restaurant.service.RestaurantUpdater;
import com.example.foodcloud.exception.NotFoundRestaurantException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/restaurant/update")
public class RestaurantUpdateController {
    private final RestaurantUpdater restaurantUpdater;
    private final RestaurantRepository restaurantRepository;

    @GetMapping("")
    public String get(@RequestParam Long restaurantId, Model model) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new NotFoundRestaurantException("Not found Restaurant"));
        model.addAttribute("restaurantInfo", restaurant);
        return "thymeleaf/restaurant/update";
    }

    @PostMapping("")
    public String post(@SessionAttribute("userId") Long userId,
                       @RequestParam Long restaurantId,
                       @Valid RestaurantUpdateControllerDto restaurantUpdateControllerDto) {

        restaurantUpdater.update(userId, restaurantId, restaurantUpdateControllerDto.convert());

        return "thymeleaf/restaurant/update";
    }
}
