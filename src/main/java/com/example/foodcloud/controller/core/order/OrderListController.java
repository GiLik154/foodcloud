package com.example.foodcloud.controller.core.order;

import com.example.foodcloud.domain.order.menu.domain.OrderMenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/order-menu")
public class OrderListController {
    private final OrderMenuRepository orderMenuRepository;

    @GetMapping("/list")
    public String get(@SessionAttribute("userId") Long userId, Model model) {
        model.addAttribute("orderMenuList", orderMenuRepository.findByUserId(userId));
        return "thymeleaf/order/list";
    }
}
