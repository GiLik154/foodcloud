package com.example.foodcloud.controller.core.order;

import com.example.foodcloud.domain.order.menu.domain.OrderMenuRepository;
import com.example.foodcloud.domain.order.menu.service.delete.OrderMenuDeleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/order")
public class OrderDeleteController {
    private final OrderMenuDeleteService orderMenuDeleteService;
    private final OrderMenuRepository orderMenuRepository;

    @GetMapping("/delete")
    public String get(Long orderMenuId, Model model) {
        model.addAttribute("orderMenu", orderMenuRepository.getValidByIdAndNotCanceled(orderMenuId));
        return "thymeleaf/order/delete";
    }

    @PostMapping("/delete")
    public String post(@SessionAttribute Long userId,
                       Long orderMenuId) {

        orderMenuDeleteService.delete(userId, orderMenuId);

        return "thymeleaf/order/delete-check";
    }
}
