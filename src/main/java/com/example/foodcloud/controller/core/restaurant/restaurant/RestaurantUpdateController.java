package com.example.foodcloud.controller.core.restaurant.restaurant;

import com.example.foodcloud.controller.core.restaurant.restaurant.dto.RestaurantUpdateControllerDto;
import com.example.foodcloud.domain.restaurant.domain.RestaurantRepository;
import com.example.foodcloud.domain.restaurant.service.update.RestaurantUpdater;
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
        model.addAttribute("restaurantInfo", restaurantRepository.getValidById(restaurantId));
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
