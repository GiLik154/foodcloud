package com.example.foodcloud.controller.core.restaurant.restaurant;

import com.example.foodcloud.controller.core.restaurant.restaurant.dto.RestaurantAddControllerDto;
import com.example.foodcloud.domain.restaurant.service.add.RestaurantAddService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/restaurant")
public class RestaurantAddController {

    private final RestaurantAddService restaurantAddService;

    @GetMapping("/add")
    public String get() {
        return "thymeleaf/restaurant/add";
    }

    @PostMapping("/add")
    public String post(@SessionAttribute("userId") Long userId,
                       @Valid RestaurantAddControllerDto restaurantAddControllerDto) {

        restaurantAddService.add(userId, restaurantAddControllerDto.convert());

        return "thymeleaf/restaurant/add";
    }

}
