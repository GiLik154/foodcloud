package com.example.foodcloud.controller.core.restaurant.restaurant;

import com.example.foodcloud.controller.core.restaurant.restaurant.dto.RestaurantUpdateControllerDto;
import com.example.foodcloud.domain.restaurant.domain.RestaurantRepository;
import com.example.foodcloud.domain.restaurant.service.update.RestaurantUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/restaurant")
public class RestaurantUpdateController {
    private final RestaurantUpdateService restaurantUpdateService;
    private final RestaurantRepository restaurantRepository;

    @GetMapping("/update")
    public String get(Long restaurantId, Model model) {
        model.addAttribute("restaurantInfo", restaurantRepository.validateRestaurant(restaurantId));
        return "thymeleaf/restaurant/update";
    }

    @PostMapping("/update")
    public String post(@SessionAttribute("userId") Long userId,
                       Long restaurantId,
                       @Valid RestaurantUpdateControllerDto restaurantUpdateControllerDto) {

        restaurantUpdateService.update(userId, restaurantId, restaurantUpdateControllerDto.convert());

        return "thymeleaf/restaurant/update";
    }
}
