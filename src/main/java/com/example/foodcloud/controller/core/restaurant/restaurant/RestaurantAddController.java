package com.example.foodcloud.controller.core.restaurant.restaurant;

import com.example.foodcloud.controller.core.restaurant.restaurant.dto.RestaurantAddControllerDto;
import com.example.foodcloud.domain.restaurant.service.add.RestaurantAddService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 식당을 추가하는 컨트롤러
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/restaurant")
public class RestaurantAddController {

    private final RestaurantAddService restaurantAddService;

    @GetMapping("/add")
    public String get() {
        return "thymeleaf/restaurant/add";
    }

    /**
     * 세션으로 userId를 받아야와하고
     * Dto를 받아오고 @Valid로 검증함
     * 이후 서비스단과 컨트롤단의 분리를 위해
     * Dto.convert 기능을 이요해서 Service Dto로 변환해줌.
     */
    @PostMapping("/add")
    public String post(@SessionAttribute("userId") Long userId,
                       @Valid RestaurantAddControllerDto restaurantAddControllerDto) {

        restaurantAddService.add(userId, restaurantAddControllerDto.convert());

        return "thymeleaf/restaurant/add";
    }

}
