package com.example.foodcloud.controller.core.restaurant.ordermanagement;

import com.example.foodcloud.domain.ordermenu.domain.OrderMenu;
import com.example.foodcloud.domain.ordermenu.domain.OrderMenuRepository;
import com.example.foodcloud.domain.ordermenu.service.OrderMenuUpdater;
import com.example.foodcloud.enums.OrderResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/restaurant/check")
public class OrderCheckController {
    private final OrderMenuRepository orderMenuRepository;
    private final OrderMenuUpdater orderMenuUpdater;

    @GetMapping("")
    public String get(Model model) {
        model.addAttribute("orderResult", OrderResult.values());
        model.addAttribute("orderMenuList", orderMenuRepository.findAll());
        return "thymeleaf/restaurant/check";
    }

    @GetMapping("/{foodMenuId}/{result}")
    @ResponseBody
    public List<OrderMenu> outOrderResult(@PathVariable("foodMenuId") Long foodMenuId, @PathVariable("result") OrderResult orderResult) {

        return orderMenuRepository.findByFoodMenuIdAndResult(foodMenuId, orderResult);
    }

    @PutMapping("/updateStatus/{orderMenuId}/{status}")
    @ResponseBody
    public void updateOrderStatus(@PathVariable("orderMenuId") Long orderMenuId, @PathVariable("status") String status) {
        orderMenuUpdater.resultUpdate(orderMenuId, status);
    }
}