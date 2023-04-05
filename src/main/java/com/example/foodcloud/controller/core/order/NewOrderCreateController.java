package com.example.foodcloud.controller.core.order;

import com.example.foodcloud.controller.core.order.dto.NewOrderCreateControllerDto;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenuRepository;
import com.example.foodcloud.domain.order.join.service.add.NewOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/order")
public class NewOrderCreateController {
    private final NewOrderService newOrderService;
    private final FoodMenuRepository foodMenuRepository;

    @GetMapping("/new")
    public String get(@RequestParam Long restaurantId, Model model) {
        model.addAttribute("foodMenuList", foodMenuRepository.findByRestaurantId(restaurantId));

        return "thymeleaf/order/new";
    }

    @PostMapping("/new")
    public String post(@SessionAttribute Long userId,
                       @Valid NewOrderCreateControllerDto newOrderCreateControllerDto) {
        Long orderMenuId = newOrderService.order(userId, newOrderCreateControllerDto.convert());

        return "redirect:/payment/pay/" + orderMenuId;
    }
}
