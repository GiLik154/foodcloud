package com.example.foodcloud.controller.core.order;

import com.example.foodcloud.domain.order.menu.domain.OrderMenuRepository;
import com.example.foodcloud.domain.order.menu.service.cancel.OrderMenuCancelService;
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
public class CancelOrderController {
    private final OrderMenuCancelService orderMenuCancelService;
    private final OrderMenuRepository orderMenuRepository;

    @GetMapping("/cancel")
    public String get(Long orderMenuId, Model model) {
        model.addAttribute("orderMenuInfo", orderMenuRepository.validateOrderMenuNotCancel(orderMenuId));
        return "thymeleaf/order/cancel";
    }

    @PostMapping("/cancel")
    public String post(@SessionAttribute Long userId,
                       Long orderMenuId,
                       Model model) {
        model.addAttribute("cancelMsg", orderMenuCancelService.cancel(userId, orderMenuId));

        return "thymeleaf/order/cancel-check";
    }
}
