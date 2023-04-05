package com.example.foodcloud.controller.core.order;

import com.example.foodcloud.controller.core.order.dto.NewOrderCreateControllerDto;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenuRepository;
import com.example.foodcloud.domain.order.join.service.add.NewOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 새로운 OrderJoinGroup을 만드는 컨트롤러
 */
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/order")
public class NewOrderCreateController {
    private final NewOrderService newOrderService;
    private final FoodMenuRepository foodMenuRepository;

    /**
     * RequestParam을 통해 restaurantId를 받아옴
     * 이후 restaurantId를 통해 foodMenuList를 반환해줌
     */
    @GetMapping("/new")
    public String get(@RequestParam Long restaurantId, Model model) {
        model.addAttribute("foodMenuList", foodMenuRepository.findByRestaurantId(restaurantId));

        return "thymeleaf/order/new";
    }

    /**
     * 세션을 통해 userId를 받아옴
     * Dto를 통해 정보를 받아오고 Valid로 검증함
     * 이후 서비스단과 컨트롤단의 분리를 위해
     * Dto.convert 기능을 이요해서 Service Dto로 변환해줌.
     */
    @PostMapping("/new")
    public String post(@SessionAttribute Long userId,
                       @Valid NewOrderCreateControllerDto newOrderCreateControllerDto) {
        Long orderMenuId = newOrderService.order(userId, newOrderCreateControllerDto.convert());

        return "redirect:/payment/pay/" + orderMenuId;
    }
}
