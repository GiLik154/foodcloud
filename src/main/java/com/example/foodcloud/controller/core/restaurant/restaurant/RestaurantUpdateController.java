package com.example.foodcloud.controller.core.restaurant.restaurant;

import com.example.foodcloud.controller.core.restaurant.restaurant.dto.RestaurantUpdateControllerDto;
import com.example.foodcloud.domain.restaurant.domain.RestaurantRepository;
import com.example.foodcloud.domain.restaurant.service.update.RestaurantUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * restaurant를 수정하는 컨트롤러
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/restaurant/update")
public class RestaurantUpdateController {
    private final RestaurantUpdateService restaurantUpdateService;
    private final RestaurantRepository restaurantRepository;

    /**
     * RequestParam으로 restaurantId 받아옴.
     * 이후 수정할 restaurant 출력해줌
     */
    @GetMapping("")
    public String get(@RequestParam Long restaurantId, Model model) {
        model.addAttribute("restaurantInfo", restaurantRepository.validateRestaurant(restaurantId));
        return "thymeleaf/restaurant/update";
    }

    /**
     * 세션으로 userId를 받아야와하고
     * RequestParam 통해 restaurantId 받아옴
     * Dto를 받아오고 @Valid로 검증함
     * 이후 서비스단과 컨트롤단의 분리를 위해
     * Dto.convert 기능을 이요해서 Service Dto로 변환해줌.
     */
    @PostMapping("")
    public String post(@SessionAttribute("userId") Long userId,
                       @RequestParam Long restaurantId,
                       @Valid RestaurantUpdateControllerDto restaurantUpdateControllerDto) {

        restaurantUpdateService.update(userId, restaurantId, restaurantUpdateControllerDto.convert());

        return "thymeleaf/restaurant/update";
    }
}
