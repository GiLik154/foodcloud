package com.example.foodcloud.controller.core.restaurant.ordermanagement;

import com.example.foodcloud.domain.order.menu.domain.OrderMenu;
import com.example.foodcloud.domain.order.menu.domain.OrderMenuRepository;
import com.example.foodcloud.domain.order.menu.service.update.OrderMenuResultUpdateService;
import com.example.foodcloud.enums.OrderResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/restaurant")
public class OrderCheckController {
    private final OrderMenuRepository orderMenuRepository;
    private final OrderMenuResultUpdateService orderMenuResultUpdateService;

    @GetMapping("/check/{foodMenuId}")
    public String get(Model model, @PathVariable String foodMenuId) {
        model.addAttribute("orderResult", OrderResult.values());
        return "thymeleaf/restaurant/check";
    }

    @GetMapping("/check/{foodMenuId}/{result}")
    @ResponseBody
    public List<OrderMenu> outOrderResult(@PathVariable("foodMenuId") Long foodMenuId, @PathVariable("result") OrderResult orderResult) {
        String result = orderResult.getResult();

        return orderMenuRepository.findByFoodMenuIdAndResult(foodMenuId, result);
    }

    @PutMapping("/updateStatus/{orderMenuId}/{status}")
    @ResponseBody
    public void updateOrderStatus(@PathVariable("orderMenuId") Long orderMenuId, @PathVariable("status") String status) {
        orderMenuResultUpdateService.update(orderMenuId, status);
    }
}
